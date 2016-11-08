package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.ChiroColleagueService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("RegistrationParticipantServiceSecurity")
public class RegistrationParticipantServiceSecurity {
    @Autowired
    private UserService userService;

    @Autowired
    private ChiroColleagueService chiroColleagueService;

    public boolean hasPermissionToSaveParticipant(RegistrationParticipant participant){
//        Boolean areColleagues = false;
        User currentUser = userService.getCurrentUser();
//        if(currentUser != null){
//            areColleagues = chiroColleagueService.isColleague(currentUser.getAdNumber(), participant.getAdNumber());
//        }

//        return currentUser != null && (currentUser.getRole().equals(SecurityRole.ADMIN) ||
//                chiroColleagueService.isColleague(Integer.parseInt(currentUser.getAdNumber()), Integer.parseInt(participant.getAdNumber())));

        /**
         * TODO TODO TODO THIS IS DUMMY, REPLACE BY LINE ABOVE
         */
        return currentUser != null && (currentUser.getRole().equals(SecurityRole.ADMIN) ||
                chiroColleagueService.isColleague(221826, Integer.parseInt(participant.getAdNumber())));
    }
}
