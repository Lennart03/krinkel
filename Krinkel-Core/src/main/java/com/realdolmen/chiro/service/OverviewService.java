package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OverviewService {

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @Autowired
    private ChiroUnitService unitService;

    public List<RegistrationParticipant> findParticipantsByGewest(String stamNumber) {
        ChiroUnit chiroUnit = unitService.find(stamNumber);

        List<RegistrationParticipant> participants = new ArrayList<>();

        for (ChiroUnit lowerUnit : chiroUnit.getLower()) {
            participants.addAll(registrationParticipantService.findParticipantsByGroup(lowerUnit.getStam()));
        }
        return participants;
    }

    public List<RegistrationParticipant> findParticipantsByVerbond(String stamNumber) {
        ChiroUnit chiroUnit = unitService.find(stamNumber);

        List<RegistrationParticipant> participants = new ArrayList<>();

        for (ChiroUnit lowerUnit : chiroUnit.getLower()) {
            participants.addAll(findParticipantsByGewest(lowerUnit.getStam()));
        }
        return participants;
    }

}
