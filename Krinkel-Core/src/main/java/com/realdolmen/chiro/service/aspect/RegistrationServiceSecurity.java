package com.realdolmen.chiro.service.aspect;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.UserService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mySecurityService")
public class RegistrationServiceSecurity {
    @Autowired
    UserService userService;

    @Autowired
    ChiroUnitService chiroUnitService;

    public boolean hasPermissionToSaveParticipant(RegistrationParticipant participant){
        User currentUser = userService.getCurrentUser();
        User chiroUser = userService.getUser(participant.getAdNumber());

        return currentUser.getRole().equals(SecurityRole.ADMIN) || currentUser.getStamnummer().equals(chiroUser.getStamnummer());
    }

    public boolean hasPermissionToSaveVolunteer(RegistrationVolunteer volunteer){
        User currentUser = userService.getCurrentUser();
        return volunteer.getAdNumber().equals(currentUser.getAdNumber());
    }
}
