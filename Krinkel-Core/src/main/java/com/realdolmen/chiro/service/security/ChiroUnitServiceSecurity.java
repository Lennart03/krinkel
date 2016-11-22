package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ChiroUnitServiceSecurity")
public class ChiroUnitServiceSecurity {
    @Autowired
    UserService userService;

    @Autowired
    ChiroUnitService chiroUnitService;

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

    //TODO change .split to regex
    //TODO use switch
    public boolean hasPermissionToSeeVerbonden(ChiroUnit chiroUnit) {
        User currentUser = userService.getCurrentUser();
        SecurityRole currentUserRole = currentUser.getRole();
        String lettersOfVerbondStamNumber;
        String  normalStamNumber = currentUser.getStamnummer().replace(" /","");

        if (currentUserRole.equals(SecurityRole.ADMIN) || currentUserRole.equals(SecurityRole.NATIONAAL)) {
            return true;
        } else {




            if(normalStamNumber.startsWith("OG")|normalStamNumber.startsWith("LEG")){
                lettersOfVerbondStamNumber = normalStamNumber.substring(0,3);
            } else {
                lettersOfVerbondStamNumber = normalStamNumber.substring(0,2);
            }
            String chiroUnitStam = chiroUnit.getStam();

            return chiroUnitStam.startsWith(lettersOfVerbondStamNumber);
        }
    }

    public boolean hasPermissionToSeeUnits(ChiroUnit chiroUnit) {
        User currentUser = userService.getCurrentUser();

        if (chiroUnit.getStam().endsWith("00")) {
            return hasPermissionToSeeGewesten(currentUser, chiroUnit);
        } else {
            return hasPermissionToSeeGroepen(currentUser,chiroUnit);

        }
    }

    //TODO use regex
    //TODO use switch?
    private boolean hasPermissionToSeeGewesten(User currentUser, ChiroUnit chiroUnit){
        SecurityRole currentUserRole = currentUser.getRole();
        String currentUserStam = currentUser.getStamnummer();

        if (currentUserRole.equals(SecurityRole.ADMIN) || currentUserRole.equals(SecurityRole.NATIONAAL) || currentUserRole.equals(SecurityRole.VERBOND)) {
            return true;
        } else {
            String beginOfStamNumber = currentUserStam.replace(" /", "").substring(0, 4);
            return chiroUnit.getStam().startsWith(beginOfStamNumber);
        }
    }

    //TODO use regex
    //TODO use switch?
    private boolean hasPermissionToSeeGroepen(User currentUser, ChiroUnit chiroUnit){
        SecurityRole currentUserRole = currentUser.getRole();
        String currentUserStam = currentUser.getStamnummer();

        if(currentUserRole.equals(SecurityRole.ADMIN) || currentUserRole.equals(SecurityRole.NATIONAAL) || currentUserRole.equals(SecurityRole.VERBOND) || currentUserRole.equals(SecurityRole.GEWEST)){
            return true;
        } else {
            return currentUserStam.replace(" /","").equals(chiroUnit.getStam());
        }
    }
}
