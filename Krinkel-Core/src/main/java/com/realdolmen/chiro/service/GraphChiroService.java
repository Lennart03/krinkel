package com.realdolmen.chiro.service;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraphChiroService {
    @Autowired
    private ChiroUnitRepository chiroUnitRepository;

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @Autowired
    private LoginLoggerRepository loginLoggerRepository;

    @Autowired
    private StamNumberTrimmer stamNumberTrimmer;

    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToMakeStatusGraph()")
    public StatusChiroUnit getStatusChiro() {
        StatusChiroUnit status = new StatusChiroUnit();
        registrationParticipantRepository.findAll().forEach(r -> {
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

        List<RawChiroUnit> allChiroUnits = findAll();

        for (RawChiroUnit chiroUnit : allChiroUnits) {
            //check if verbond exists
            if (getGraphChiroUnitByLowerUnitName(root.getChildren(), chiroUnit.getVerbondNaam()) == null) {
                GraphChiroUnit verbond = new GraphChiroUnit(chiroUnit.getVerbondNaam(), null, new ArrayList<GraphChiroUnit>());
                GraphChiroUnit gewest = new GraphChiroUnit(chiroUnit.getGewestNaam(), null, new ArrayList<GraphChiroUnit>());
                GraphChiroUnit groep = new GraphChiroUnit(chiroUnit.getGroepNaam(), findParticipants(chiroUnit.getGroepStamNummer()), null);

                gewest.getChildren().add(groep);
                verbond.getChildren().add(gewest);
                root.getChildren().add(verbond);
            } else {
                GraphChiroUnit verbond = getGraphChiroUnitByLowerUnitName(root.getChildren(), chiroUnit.getVerbondNaam());
                //check if gewest exists
                if (getGraphChiroUnitByLowerUnitName(verbond.getChildren(), chiroUnit.getGewestNaam()) == null) {
                    GraphChiroUnit gewest = new GraphChiroUnit(chiroUnit.getGewestNaam(), null, new ArrayList<GraphChiroUnit>());
                    GraphChiroUnit groep = new GraphChiroUnit(chiroUnit.getGroepNaam(), findParticipants(chiroUnit.getGroepStamNummer()), null);

                    gewest.getChildren().add(groep);
                    verbond.getChildren().add(gewest);
                } else {
                    GraphChiroUnit verbondForGroep = getGraphChiroUnitByLowerUnitName(root.getChildren(), chiroUnit.getVerbondNaam());
                    GraphChiroUnit gewestForGroep = getGraphChiroUnitByLowerUnitName(verbondForGroep.getChildren(), chiroUnit.getGewestNaam());

                    GraphChiroUnit groep = new GraphChiroUnit(chiroUnit.getGroepNaam(), findParticipants(chiroUnit.getGroepStamNummer()), null);
                    gewestForGroep.getChildren().add(groep);
                }
            }
        }
        return root;
    }

    private GraphChiroUnit getGraphChiroUnitByLowerUnitName(List<GraphChiroUnit> units, String unitName) {
        for (GraphChiroUnit unit : units) {
            if (unit.getName().equals(unitName)) {
                return unit;
            }
        }
        return null;
    }

    private Integer findParticipants(String stamNumber) {
        String normalizedStamNumber = stamNumberTrimmer.trim(stamNumber);

        int participants = registrationParticipantService.findParticipantsByGroup(normalizedStamNumber).size();
        int volunteers = registrationParticipantService.findVolunteersByGroup(normalizedStamNumber).size();
        return participants + volunteers;
    }

    private List<RawChiroUnit> findAll() {
        return chiroUnitRepository.findAll();
    }
/* commented by thomas no longer needed
    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToGetLoginData()")
    public SortedMap getLoginDataFromLastTwoWeeks() {
        return getUniqueLoginsPerVerbond(startDate,endDate);
    }
*/
    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToGetLoginData()")
    public SortedMap getLoginData(Date startDate,Date endDate) {
        return getUniqueLoginsPerVerbond(startDate,endDate);
    }


    private SortedMap<Verbond, SortedMap<String, Integer>> getUniqueLoginsPerVerbond(Date startDate,Date endDate) {
        TreeMap<Verbond, SortedMap<String, Integer>> uniqueLoginsPerVerbond = new TreeMap<>();
        List<LoginLog> allLogs = null;
        List<String> distinctStamps;

        fillMapWithAllVerbonden(uniqueLoginsPerVerbond);
        SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");

            allLogs = loginLoggerRepository.findLogsBetweenDates(startDate, endDate);

            distinctStamps = loginLoggerRepository.findDistinctStamps(startDate, endDate)
                    .stream()
                    .map(date->frmt.format(date))
                    .collect(Collectors.toList());

        allLogs.parallelStream().forEach(log -> mapLoginLogs(log, uniqueLoginsPerVerbond));

        uniqueLoginsPerVerbond.forEach((key, value) -> {
            uniqueLoginsPerVerbond.put(key, fillMapWithZeroValuesOnDaysWhereThereAreNoLogins(value, distinctStamps));
        });


        for(Map.Entry<Verbond, SortedMap<String, Integer>> entry : uniqueLoginsPerVerbond.entrySet())
        {
            System.out.println("key "+entry.getKey());
            System.out.println("value "+entry.getValue());
        }
        return uniqueLoginsPerVerbond;
    }

    private void mapLoginLogs(LoginLog log, SortedMap<Verbond, SortedMap<String, Integer>> treeMap) {
        Verbond verbondFromStamNumber = Verbond.getVerbondFromStamNumber(log.getStamNumber());
        SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
        SortedMap<String, Integer> dateIntegerSortedMap = fillMapWithDateToLoginCount(frmt.format(log.getStamp()), treeMap.get(verbondFromStamNumber));

        treeMap.put(verbondFromStamNumber, dateIntegerSortedMap);
    }

    private SortedMap<String, Integer> fillMapWithZeroValuesOnDaysWhereThereAreNoLogins(SortedMap<String, Integer> sortedMap, List<String> distinctStamps) {
        if (sortedMap.size() != distinctStamps.size()) {
            distinctStamps.parallelStream().forEach(stamp -> {
                if (!sortedMap.containsKey(stamp)) {
                    sortedMap.put(stamp, 0);
                }
            });
        }
        /*
         * I know it refers to the same object, but I prefer this way of coding because it looks cleaner to me
         */
        return sortedMap;
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
     * This method should only be used on empty maps.
     *
     * @param map
     */
    private void fillMapWithAllVerbonden(SortedMap<Verbond, SortedMap<String, Integer>> map) {
        for (Verbond verbond : Verbond.values()) {
            TreeMap<String, Integer> uniqueLoginsPerDate = new TreeMap<>();
            map.put(verbond, uniqueLoginsPerDate);
        }
    }
}
