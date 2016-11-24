package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.GraphLoginCount;
import com.realdolmen.chiro.domain.EventRole;
import com.realdolmen.chiro.domain.LoginLog;
import com.realdolmen.chiro.domain.Verbond;
import com.realdolmen.chiro.domain.units.GraphChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.domain.units.StatusChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.repository.LoginLoggerRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.util.StamNumberTrimmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphChiroService {
    @Autowired
    private ChiroUnitRepository repository;

    @Autowired
    private RegistrationParticipantRepository participantRepository;

    @Autowired
    private RegistrationParticipantService participantService;

    @Autowired
    private LoginLoggerRepository loggerRepository;

    @Autowired
    private StamNumberTrimmer stamNumberTrimmer;

    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToMakeStatusGraph()")
    public StatusChiroUnit getStatusChiro() {
        StatusChiroUnit status = new StatusChiroUnit();
        participantRepository.findAll().forEach(r -> {
            if (r.getEventRole().equals(EventRole.VOLUNTEER)) {
                switch (r.getStatus()) {
                    case PAID:
                        status.setVolunteersNotConfirmed(status.getVolunteersNotConfirmed() + 1);
                        break;
                    case CONFIRMED:
                        status.setVolunteersConfirmed(status.getVolunteersConfirmed() + 1);
                        break;
                    case TO_BE_PAID:
                        status.setVolunteersNotPaid(status.getVolunteersNotPaid() + 1);
                        break;
                }
            } else {
                switch (r.getStatus()) {
                    case PAID:
                        status.setParticipantsNotConfirmed(status.getParticipantsNotConfirmed() + 1);
                        break;
                    case CONFIRMED:
                        status.setParticipantsConfirmed(status.getParticipantsConfirmed() + 1);
                        break;
                    case TO_BE_PAID:
                        status.setParticipantsNotPaid(status.getParticipantsNotPaid() + 1);
                        break;
                }
            }

        });


        return status;
    }

    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToMakeSunGraph()")
    public GraphChiroUnit summary() {
        GraphChiroUnit root = new GraphChiroUnit("Inschrijvingen", null, new ArrayList<GraphChiroUnit>());

        boolean verbondadded;
        boolean gewestadded;


        /**
         * VERY long execution time. Changed the multiple findAll() to one findAll(), which reduced the execution time from ~8 seconds to ~2.5 seconds.
         */
        List<RawChiroUnit> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            RawChiroUnit raw = all.get(i);
            verbondadded = false;
            gewestadded = false;
            for (GraphChiroUnit r : root.getChildren()) {

                if (r.getName().equals(raw.getVerbondName())) {
                    verbondadded = true;
                    for (GraphChiroUnit r2 : r.getChildren()) {
                        if (r2.getName().equals(raw.getGewestName())) {
                            gewestadded = true;
                            r2.getChildren().add(new GraphChiroUnit(raw.getName(), findParticipants(raw.getStamNumber()), null));
                        }
                    }
                    if (!gewestadded) {
                        r.getChildren().add(new GraphChiroUnit(raw.getGewestName(), null, new ArrayList<GraphChiroUnit>()));
                        i--;
                    }
                }

            }

            if (!verbondadded) {
                root.getChildren().add(new GraphChiroUnit(raw.getVerbondName(), null, new ArrayList<GraphChiroUnit>()));
                i--;
            }
        }
        return root;
    }

    private Integer findParticipants(String stamNumber) {
        String normalizedStamNumber = stamNumberTrimmer.trim(stamNumber);

        int participants = participantService.findParticipantsByGroup(normalizedStamNumber).size();
        int volunteers = participantService.findVolunteersByGroup(normalizedStamNumber).size();
        return participants + volunteers;
    }

    private List<RawChiroUnit> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToGetLoginData()")
    public List<GraphLoginCount> getLoginData() {
        return loggerRepository.crunchData();
    }

    public SortedMap<Verbond, SortedMap<String, Integer>> getUniqueLoginsPerVerbond() {
        TreeMap<Verbond, SortedMap<String, Integer>> uniqueLoginsPerVerbond = new TreeMap<>();
        fillMapWithAllVerbonden(uniqueLoginsPerVerbond);

        List<LoginLog> allLogs = loggerRepository.findAll();

        allLogs.parallelStream().forEach(log -> mapLoginLogs(log, uniqueLoginsPerVerbond));

        return uniqueLoginsPerVerbond;
    }

    private void mapLoginLogs(LoginLog log, SortedMap<Verbond, SortedMap<String, Integer>> treeMap) {
        Verbond verbondFromStamNumber = Verbond.getVerbondFromStamNumber(log.getStamNumber());

        SortedMap<String, Integer> dateIntegerSortedMap = fillMapWithDateToLoginCount(log.getStamp().toString(), treeMap.get(verbondFromStamNumber));

        treeMap.put(verbondFromStamNumber, dateIntegerSortedMap);
    }




    private SortedMap<String, Integer> fillMapWithDateToLoginCount(String date, SortedMap<String, Integer> map) {
        if (map.containsKey(date)) {
            map.put(date, map.get(date) + 1);
        } else {
            map.put(date, 1);
        }
        return map;
    }

    /**
     * Initializes a newly created SortedMap with all Verbonden in it.
     * No checks necessary because this method should only be used on empty maps.
     * @param map
     */
    private void fillMapWithAllVerbonden(SortedMap<Verbond, SortedMap<String, Integer>> map) {
        for (Verbond verbond : Verbond.values()) {
            TreeMap<String, Integer> uniqueLoginsPerDate = new TreeMap<>();
            map.put(verbond, uniqueLoginsPerDate);
        }
    }
}
