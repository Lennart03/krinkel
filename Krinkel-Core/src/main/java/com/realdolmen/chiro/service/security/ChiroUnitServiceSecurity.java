package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.Verbond;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.vo.RolesAndUpperClasses;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("ChiroUnitServiceSecurity")
public class ChiroUnitServiceSecurity {
    @Autowired
    UserService userService;

    @Autowired
    ChiroUnitService chiroUnitService;

    //ChiroUnitService.findVerbondUnits()
    public boolean hasPermissionToFindVerbonden() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null;
    }

    public boolean hasPermissionToSeeVerbonden(ChiroUnit chiroUnit) {
        //get current user and some shit he has
        User currentUser = userService.getCurrentUser();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = currentUser.getRolesAndUpperClassesByStam();
        String currentUserStamNumber = currentUser.getStamnummer();
        List<SecurityRole> securityRolesWithAccesToData = new ArrayList<>();
        securityRolesWithAccesToData.add(SecurityRole.NATIONAAL);

        //role only gets set when admin but you never know
        //admin may see all verbonden
        if (currentUser.getRole() != null && currentUser.getRole().equals(SecurityRole.ADMIN)) {
            return true;
            //if map size=1 there is only one role
            //if map size=1 there is only one stamnummer => only one verbond
            //can see verbond if nationaal or you are in a groep of the verbond
        } else if (rolesAndUpperClassesByStam.size() == 1) {
            Verbond verbondOfUser = Verbond.getVerbondFromStamNumber(currentUserStamNumber);
            return chiroUnit.getStam().equals(verbondOfUser.getStam()) || rolesAndUpperClassesByStam.get(currentUserStamNumber).getSecurityRole().equals(SecurityRole.NATIONAAL);
            // there may be multiple roles
            // may be in multiple verbonden
        } else if (rolesAndUpperClassesByStam.size() > 1) {
            return testMethod(rolesAndUpperClassesByStam, chiroUnit, securityRolesWithAccesToData);
        } else {
            return false;
        }
    }

    private boolean testMethod(Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam, ChiroUnit chiroUnit, List<SecurityRole> securityRolesWithAccesToData) {
        List<SecurityRole> allSecurityRoles = new ArrayList<>();
        List<String> allVerbondenStamNumbers = new ArrayList<>();
        boolean isInVerbond = false;
        boolean hasCorrectRole = false;

        for (Map.Entry<String, RolesAndUpperClasses> entry : rolesAndUpperClassesByStam.entrySet()) {
            if (!entry.getKey().startsWith("NAT")) {
                allVerbondenStamNumbers.add(Verbond.getVerbondFromStamNumber(entry.getKey()).getStam());
            }
            allSecurityRoles.add(entry.getValue().getSecurityRole());
        }
        if (allVerbondenStamNumbers.contains(chiroUnit.getStam())) {
            isInVerbond = true;
        }
        List<SecurityRole> common = allSecurityRoles.stream().filter(securityRolesWithAccesToData::contains).collect(Collectors.toList());
        if (!common.isEmpty()) {
            hasCorrectRole = true;
        }
        return isInVerbond || hasCorrectRole;
    }


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

    public boolean hasPermissionToSeeUnits(ChiroUnit chiroUnit) {
        User currentUser = userService.getCurrentUser();

        if (chiroUnit.getStam().endsWith("00")) {
            return hasPermissionToSeeGewesten(currentUser, chiroUnit);
        } else {
            return hasPermissionToSeeGroepen(currentUser, chiroUnit);

        }
    }

    //TODO use regex
    //TODO use switch?
    private boolean hasPermissionToSeeGewesten(User currentUser, ChiroUnit chiroUnit) {
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
    private boolean hasPermissionToSeeGroepen(User currentUser, ChiroUnit chiroUnit) {
        SecurityRole currentUserRole = currentUser.getRole();
        String currentUserStam = currentUser.getStamnummer();

        if (currentUserRole.equals(SecurityRole.ADMIN) || currentUserRole.equals(SecurityRole.NATIONAAL) || currentUserRole.equals(SecurityRole.VERBOND) || currentUserRole.equals(SecurityRole.GEWEST)) {
            return true;
        } else {
            return currentUserStam.replace(" /", "").equals(chiroUnit.getStam());
        }
    }
}
