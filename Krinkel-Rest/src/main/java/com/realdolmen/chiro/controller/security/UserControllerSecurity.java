package com.realdolmen.chiro.controller.security;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserControllerSecurity")
public class UserControllerSecurity {
    @Autowired
    UserService userService;

    public boolean hasPermissionToGetUser(String adNumber) {
        User currentUser = userService.getCurrentUser();
        return currentUser != null && currentUser.getAdNumber().equals(adNumber);
    }
}
