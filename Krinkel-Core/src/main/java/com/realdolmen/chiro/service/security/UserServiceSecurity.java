package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.SecurityRole;
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

    /**
     * Returns true if current logged in user is admin, false otherwise.
     * @return
     */
    public boolean hasAdminRights() {
        User currentUser = userService.getCurrentUser();
        if(currentUser!= null && currentUser.getRole() == SecurityRole.ADMIN) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasPermissionToSubscribeUser() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null;
    }


}
