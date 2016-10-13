package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.GraphLoginCount;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.units.GraphChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.domain.units.StatusChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.repository.LoginLoggerRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WVDAZ49 on 12/10/2016.
 */
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

    public StatusChiroUnit getStatusChiro(){
        StatusChiroUnit status = new StatusChiroUnit();
        participantRepository.findAll().forEach(r -> {
            if(r.getRole().equals(Role.VOLUNTEER)){
                switch (r.getStatus()){
                    case PAID: status.setVolunteersNotConfirmed(status.getVolunteersNotConfirmed() + 1);
                    case CONFIRMED: status.setVolunteersConfirmed(status.getVolunteersConfirmed() + 1);
                    case TO_BE_PAID: status.setVolunteersNotPaid(status.getVolunteersNotPaid() + 1);
                }
            } else {
                switch (r.getStatus()){
                    case PAID: status.setParticipantsNotConfirmed(status.getParticipantsNotConfirmed() + 1);
                    case CONFIRMED: status.setParticipantsConfirmed(status.getParticipantsConfirmed() + 1);
                    case TO_BE_PAID: status.setParticipantsNotPaid(status.getParticipantsNotPaid() + 1);
                }
            }

        });


        return status;
    }

    public GraphChiroUnit summary() {
        GraphChiroUnit root = new GraphChiroUnit("Inschrijvingen", null, new ArrayList<GraphChiroUnit>());
//
        boolean verbondadded;
        boolean gewestadded;

        for (int i = 0; i < findAll().size(); i++) {
            RawChiroUnit raw = findAll().get(i);
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
        String stamNumber1 = stamNumber.substring(0,2);
        String stamNumber2 = stamNumber.substring(4);
        stamNumber = stamNumber1 + stamNumber2;
        int participants = participantService.findParticipantsByGroup(stamNumber).size();
        int volunteers = participantService.findVolunteersByGroup(stamNumber).size();
        return participants + volunteers;
    }


    private List<RawChiroUnit> findAll() {
        return repository.findAll();
    }
    public List<GraphLoginCount> getLoginData(){
        return loggerRepository.crunchData();
    }
}
