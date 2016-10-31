package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationParticipantServiceSecurity {
    @Autowired
    UserService userService;

    public boolean hasPermissionToSaveParticipant(RegistrationParticipant participant){
        User currentUser = userService.getCurrentUser();
        User chiroUser = userService.getUser(participant.getAdNumber());

        return currentUser != null && (currentUser.getRole().equals(SecurityRole.ADMIN) || currentUser.getStamnummer().equals(chiroUser.getStamnummer()));
    }
}
