package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationVolunteerServiceSecurity {
    @Autowired
    UserService userService;

    public boolean hasPermissionToSaveVolunteer(RegistrationVolunteer volunteer) {
        User currentUser = userService.getCurrentUser();
        return currentUser != null && volunteer.getAdNumber().equals(currentUser.getAdNumber());
    }
}
