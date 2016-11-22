package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.service.ChiroPloegService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ChiroUnitServiceSecurity")
public class ChiroUnitServiceSecurity {
    @Autowired
    UserService userService;

    @Autowired
    ChiroPloegService chiroPloegService;

    public boolean hasPermissionToGetColleagues() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null;
    }

    public boolean hasPermissionToSeeColleagues(User user) {
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getStamnummer().equals(user.getStamnummer());
    }

    public boolean hasPermissionToGetVolunteers() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getRole().equals(SecurityRole.ADMIN);
    }

    public boolean hasPermissionToGetParticipants() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getRole().equals(SecurityRole.ADMIN);
    }

    public boolean hasPermissionToFindUnits() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getRole().equals(SecurityRole.ADMIN);
    }

    //ChiroUnitService.findVerbondUnits()
    public boolean hasPermissionToFindVerbonden() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null;
    }

    public boolean hasPermissionToSeeVerbonden(ChiroUnit chiroUnit) {
        User currentUser = userService.getCurrentUser();
        SecurityRole currentUserRole = currentUser.getRole();

        if (currentUserRole.equals(SecurityRole.ADMIN) || currentUserRole.equals(SecurityRole.NATIONAAL)) {
            return true;
        } else {
            String substring = currentUser.getStamnummer().substring(0, 2);
            String chiroUnitStam = chiroUnit.getStam();
            return chiroUnitStam.contains(substring);
        }
    }

    public boolean hasPermissionToSeeUnits(ChiroUnit chiroUnit) {
        User currentUser = userService.getCurrentUser();
        SecurityRole currentUserRole = currentUser.getRole();
        System.out.println(chiroUnit.getStam());

//        if (chiroUnit.getStam().endsWith("00")) {
//        } else {
//
//        }

        return true;
    }
}
