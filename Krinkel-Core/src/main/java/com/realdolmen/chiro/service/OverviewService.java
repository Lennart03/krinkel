package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OverviewService {

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @Autowired
    private ChiroUnitService unitService;

    public List<RegistrationParticipant> findParticipantsByGewest(String stamNumber) {
        ChiroUnit chiroUnit = unitService.find(stamNumber);

        for (ChiroUnit lowerUnit : chiroUnit.getLower()) {
            return registrationParticipantService.findParticipantsByGroup(lowerUnit.getStam());
        }
        return null;
    }

    public List<RegistrationParticipant> findParticipantsByVerbond(String stamNumber){
        ChiroUnit chiroUnit = unitService.find(stamNumber);

        for (ChiroUnit lowerUnit: chiroUnit.getLower()){
            return findParticipantsByGewest(lowerUnit.getStam());
        }
        return null;
    }

}
