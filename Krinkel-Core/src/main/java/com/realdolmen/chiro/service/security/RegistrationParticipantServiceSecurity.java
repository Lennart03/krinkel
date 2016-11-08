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
    UserService userService;

    @Autowired
    ChiroColleagueService chiroColleagueService;

    public boolean hasPermissionToSaveParticipant(RegistrationParticipant participant){
        Boolean areColleagues = false;
        User currentUser = userService.getCurrentUser();
        if(currentUser != null){
            areColleagues = chiroColleagueService.isColleague(currentUser.getAdNumber(), participant.getAdNumber());
        }

        return currentUser != null && (currentUser.getRole().equals(SecurityRole.ADMIN) || areColleagues.equals(true));
    }
}
