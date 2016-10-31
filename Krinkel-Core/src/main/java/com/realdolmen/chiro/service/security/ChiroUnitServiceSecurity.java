package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ChiroUnitServiceSecurity")
public class ChiroUnitServiceSecurity {
    @Autowired
    UserService userService;

    public boolean hasPermissionToGetColleagues(){
        User currentUser = userService.getCurrentUser();

        return currentUser != null;
    }

    public boolean hasPersmissionToSeeColleaggues(String stamnummer){
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getStamnummer().equals(stamnummer);
    }
}
