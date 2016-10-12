package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
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

    public int[] findParticipantsByGewest(String stamNumber) {
        ChiroUnit chiroUnit = unitService.find(stamNumber);
        return fillParticipantsArray(chiroUnit);
    }

    public int[] findParticipantsByVerbond(String stamNumber) {
        ChiroUnit chiroUnit = unitService.find(stamNumber);

        int[] participantsAndVolunteers = new int[2];

        for (ChiroUnit lowerUnit : chiroUnit.getLower()) {
            participantsAndVolunteers[0] += findParticipantsByGewest(lowerUnit.getStam())[0];
            participantsAndVolunteers[1] += findParticipantsByGewest(lowerUnit.getStam())[1];
        }
        return participantsAndVolunteers;
    }

    public int[] fillParticipantsArray(ChiroUnit chiroUnit) {
        List<RegistrationParticipant> participants = new ArrayList<>();
        List<RegistrationVolunteer> volunteers = new ArrayList<>();
        int[] participantsAndVolunteers = new int[2];

        for (ChiroUnit lowerUnit : chiroUnit.getLower()) {
            participants.addAll(registrationParticipantService.findParticipantsByGroup(lowerUnit.getStam()));
            volunteers.addAll(registrationParticipantService.findVolunteersByGroup(lowerUnit.getStam()));
            participantsAndVolunteers[0] = participants.size();
            participantsAndVolunteers[1] = volunteers.size();
        }

        return participantsAndVolunteers;
    }

}
