package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserServiceSecurity")
public class UserServiceSecurity {
    @Autowired
    private UserService userService;

    public boolean hasPermissionToGetColleagues() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null;
    }


}
