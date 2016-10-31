package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("GraphChiroServiceSecurity")
public class GraphChiroServiceSecurity {
    @Autowired
    UserService userService;

    @Autowired
    ChiroUnitService chiroUnitService;

    public boolean hasPermissionToMakeSunGraph(){
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getRole().equals(SecurityRole.ADMIN);
    }

    public boolean hasPermissionToMakeStatusGraph(){
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getRole().equals(SecurityRole.ADMIN);
    }

    public boolean hasPermissionToGetLoginData(){
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getRole().equals(SecurityRole.ADMIN);
    }


}
