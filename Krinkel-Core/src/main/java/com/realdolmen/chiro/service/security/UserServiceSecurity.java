package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("GraphChiroServiceSecurity")
public class UserServiceSecurity {
    @Autowired
    UserService userService;

    public boolean hasPermissionToGetUser(String adNumber){
        User currentUser = userService.getCurrentUser();
        return currentUser.getAdNumber().equals(adNumber);
    }
}
