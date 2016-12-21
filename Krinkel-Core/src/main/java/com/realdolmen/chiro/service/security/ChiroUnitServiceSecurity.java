package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.vo.RolesAndUpperClasses;
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
        if (currentUser.getRole().equals(SecurityRole.ADMIN)) {
            return true;
        } else if (rolesAndUpperClassesByStam.size() == 1 && currentUserStamNumber.startsWith("NAT")){
            return true;
            //if map size=1 there is only one role
            //if map size=1 there is only one stamnummer => only one verbond
            //can see verbond if nationaal or you are in a groep of the verbond
        } else if (rolesAndUpperClassesByStam.size() == 1 && !currentUserStamNumber.startsWith("NAT")) {
            Verbond verbondOfUser = Verbond.getVerbondFromStamNumber(currentUserStamNumber);
            return chiroUnit.getStamNummer().equals(verbondOfUser.getStam());
            // there may be multiple roles
            // may be in multiple verbonden
        } else if (rolesAndUpperClassesByStam.size() > 1) {
            return checkMultipleStamNumbersForPermissionToSeeVerbond(rolesAndUpperClassesByStam, chiroUnit, securityRolesWithAccesToData);
        } else {
            return false;
        }
    }

    private boolean checkMultipleStamNumbersForPermissionToSeeVerbond(Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam, ChiroUnit chiroUnit, List<SecurityRole> securityRolesWithAccesToData) {
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
        if (allVerbondenStamNumbers.contains(chiroUnit.getStamNummer())) {
            isInVerbond = true;
        }
        List<SecurityRole> common = allSecurityRoles.stream().filter(securityRolesWithAccesToData::contains).collect(Collectors.toList());
        if (!common.isEmpty()) {
            hasCorrectRole = true;
        }
        return isInVerbond || hasCorrectRole;
    }

    public boolean hasPermissionToSeeUnits(ChiroUnit chiroUnit) {
        User currentUser = userService.getCurrentUser();

        if (chiroUnit.getStamNummer().endsWith("00")) {
            return hasPermissionToSeeGewesten(currentUser, chiroUnit);
        } else {
            return hasPermissionToSeeGroepen(currentUser, chiroUnit);
        }
    }


    private boolean hasPermissionToSeeGewesten(User currentUser, ChiroUnit chiroUnit) {
        //get current user and some shit he has
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = currentUser.getRolesAndUpperClassesByStam();
        String currentUserStamNumber = currentUser.getStamnummer();
        List<SecurityRole> securityRolesWithAccesToData = new ArrayList<>();
        securityRolesWithAccesToData.add(SecurityRole.NATIONAAL);
        securityRolesWithAccesToData.add(SecurityRole.VERBOND);

        if (currentUser.getRole().equals(SecurityRole.ADMIN)) {
            return true;
        } else if (rolesAndUpperClassesByStam.size() == 1) {
            return checkStamNumberForPermissionToSeeGewest(rolesAndUpperClassesByStam, chiroUnit, securityRolesWithAccesToData, currentUserStamNumber);
        } else if (rolesAndUpperClassesByStam.size() > 1) {
            return checkMultipleStamNumbersForPermissionToSeeGewest(rolesAndUpperClassesByStam, chiroUnit, securityRolesWithAccesToData);
        } else {
            return false;
        }
    }

    private boolean checkStamNumberForPermissionToSeeGewest(Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam, ChiroUnit chiroUnit, List<SecurityRole> securityRolesWithAccesToData, String currentUserStamNumber) {
        if (securityRolesWithAccesToData.contains(rolesAndUpperClassesByStam.get(currentUserStamNumber).getSecurityRole())) {//you have the correct role
            return true;
        } else if (chiroUnit.getStamNummer().equals(rolesAndUpperClassesByStam.get(currentUserStamNumber).getStamNumberUpperUnit())) {//you are in this gewest
            return true;
        } else if (chiroUnit.getStamNummer().equals(currentUserStamNumber)) {//you are this gewest
            return true;
        } else {
            return false;
        }
    }

    private boolean checkMultipleStamNumbersForPermissionToSeeGewest(Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam, ChiroUnit chiroUnit, List<SecurityRole> securityRolesWithAccesToData) {
        for (Map.Entry<String, RolesAndUpperClasses> entry : rolesAndUpperClassesByStam.entrySet()) {
            if (entry.getValue().getSecurityRole().equals(SecurityRole.NATIONAAL)) {
                return true;
            } else if (entry.getKey().equals(chiroUnit.getStamNummer())) { //you are this gewest
                return true;
            } else if (entry.getKey().equals(chiroUnit.getUpper().getStamNummer())) { //you are its verbond
                return true;
            } else if (entry.getValue().getStamNumberUpperUnit().equals(chiroUnit.getStamNummer())) {//you are in this gewest
                return true;
            }
        }
        return false;
    }

    private boolean hasPermissionToSeeGroepen(User currentUser, ChiroUnit chiroUnit) {
        //get current user and some shit he has
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = currentUser.getRolesAndUpperClassesByStam();
        String currentUserStamNumber = currentUser.getStamnummer();
        List<SecurityRole> securityRolesWithAccesToData = new ArrayList<>();
        securityRolesWithAccesToData.add(SecurityRole.NATIONAAL);
        securityRolesWithAccesToData.add(SecurityRole.GEWEST);
        securityRolesWithAccesToData.add(SecurityRole.VERBOND);

        if (currentUser.getRole() != null && currentUser.getRole().equals(SecurityRole.ADMIN)) {
            return true;
        } else if (rolesAndUpperClassesByStam.size() == 1) {
            return checkStamNumberForPermissionToSeeGroep(rolesAndUpperClassesByStam, chiroUnit, securityRolesWithAccesToData, currentUserStamNumber);
        } else if (rolesAndUpperClassesByStam.size() > 1) {
            return checkMultipleStamNumbersForPermissionToSeeGroep(rolesAndUpperClassesByStam, chiroUnit, securityRolesWithAccesToData);
        } else {
            return false;
        }
    }

    private boolean checkStamNumberForPermissionToSeeGroep(Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam, ChiroUnit chiroUnit, List<SecurityRole> securityRolesWithAccesToData, String currentUserStamNumber) {
        if (securityRolesWithAccesToData.contains(rolesAndUpperClassesByStam.get(currentUserStamNumber).getSecurityRole())) {//je hebt de correcte rol
            return true;
        } else if (chiroUnit.getUpper().getStamNummer().equals(currentUserStamNumber)) {//je bent het gewest
            return true;
        } else if (chiroUnit.getStamNummer().equals(currentUserStamNumber)) { //je eigen groep
            return true;
        } else {
            return false;
        }
    }

    private boolean checkMultipleStamNumbersForPermissionToSeeGroep(Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam, ChiroUnit chiroUnit, List<SecurityRole> securityRolesWithAccesToData) {
        for (Map.Entry<String, RolesAndUpperClasses> entry : rolesAndUpperClassesByStam.entrySet()) {
            if (entry.getValue().getSecurityRole().equals(SecurityRole.NATIONAAL)) {
                return true;
            } else if (Verbond.getVerbondFromStamNumber(chiroUnit.getStamNummer()).getStam().equals(entry.getKey())) {//je bent het verbond
                return true;
            } else if (chiroUnit.getUpper().getStamNummer().equals(entry.getKey())) {//je bent het gewest
                return true;
            } else if (chiroUnit.getStamNummer().equals(entry.getKey())) {//je bent de groep
                return true;
            }
        }
        return false;
    }

    public boolean hasPermissionToGetParticipants() {
        User currentUser = userService.getCurrentUser();
        String currentUserStamNumber = currentUser.getStamnummer();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = currentUser.getRolesAndUpperClassesByStam();
        List<SecurityRole> securityRolesWithAcces = new ArrayList<>();
        securityRolesWithAcces.add(SecurityRole.ADMIN);
        securityRolesWithAcces.add(SecurityRole.GROEP);

        if (rolesAndUpperClassesByStam.size() == 1) {
            return securityRolesWithAcces.contains(rolesAndUpperClassesByStam.get(currentUserStamNumber).getSecurityRole());
        } else if (rolesAndUpperClassesByStam.size() > 1) {
            for (Map.Entry<String, RolesAndUpperClasses> entry : rolesAndUpperClassesByStam.entrySet()) {
                if (securityRolesWithAcces.contains(entry.getValue().getSecurityRole())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasPermissionToGetVolunteers() {
        User currentUser = userService.getCurrentUser();
        String currentUserStamNumber = currentUser.getStamnummer();
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = currentUser.getRolesAndUpperClassesByStam();
        List<SecurityRole> securityRolesWithAcces = new ArrayList<>();
        securityRolesWithAcces.add(SecurityRole.ADMIN);
        securityRolesWithAcces.add(SecurityRole.VERBOND);
        securityRolesWithAcces.add(SecurityRole.GROEP);

        if (rolesAndUpperClassesByStam.size() == 1) {
            return currentUser.getRole().equals(SecurityRole.ADMIN) || securityRolesWithAcces.contains(rolesAndUpperClassesByStam.get(currentUserStamNumber).getSecurityRole());
        } else if (rolesAndUpperClassesByStam.size() > 1) {
            if (currentUser.getRole().equals(SecurityRole.ADMIN)) {
                return true;
            }
            for (Map.Entry<String, RolesAndUpperClasses> entry : rolesAndUpperClassesByStam.entrySet()) {
                if (securityRolesWithAcces.contains(entry.getValue().getSecurityRole())) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean hasPermissionToSeeParticipants(RegistrationParticipant registrationParticipant) {
        User currentUser = userService.getCurrentUser();
        String registrationParticipantNormalStamNumber = registrationParticipant.getStamnumber().replace(" /", "");
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = currentUser.getRolesAndUpperClassesByStam();

        if (rolesAndUpperClassesByStam.size() == 1) {
            if (currentUser.getRole().equals(SecurityRole.ADMIN) || currentUser.getAdNumber().equals(registrationParticipant.getAdNumber())) {//check if you are a admin or you are the user
                return true;
            } else if (currentUser.getStamnummer().equals(registrationParticipantNormalStamNumber)) {
                registrationParticipant.setEatinghabbit(null);
                registrationParticipant.setMedicalRemarks("");
                return true;
            } else {
                return false;
            }
        } else if (rolesAndUpperClassesByStam.size() > 1) {
            if (SecurityRole.ADMIN.equals(currentUser.getRole())) {
                return true;
            }
            for (Map.Entry<String, RolesAndUpperClasses> entry : rolesAndUpperClassesByStam.entrySet()) {
                if (currentUser.getAdNumber().equals(registrationParticipant.getAdNumber())) {
                    return true;
                } else if (entry.getKey().equals(registrationParticipantNormalStamNumber)) {
                    registrationParticipant.setEatinghabbit(null);
                    registrationParticipant.setMedicalRemarks("");
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean hasPermissionToSeeVolunteers(RegistrationVolunteer registrationVolunteer) {
        User currentUser = userService.getCurrentUser();
        String registrationVolunteerNormalStamNumber = registrationVolunteer.getStamnumber().replace(" /", "");
        Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = currentUser.getRolesAndUpperClassesByStam();

        if (rolesAndUpperClassesByStam.size() == 1) {
            if (SecurityRole.ADMIN.equals(currentUser.getRole())) {
                return true;
            } else if (rolesAndUpperClassesByStam.get(registrationVolunteerNormalStamNumber).getSecurityRole().equals(SecurityRole.VERBOND)) {
                registrationVolunteer.setAddress(null);
                registrationVolunteer.setEatinghabbit(null);
                registrationVolunteer.setMedicalRemarks("");
                registrationVolunteer.setFunction(null);
                registrationVolunteer.setCampGround(null);
                return true;
            } else if (registrationVolunteer.getAdNumber().equals(currentUser.getAdNumber())) {
                return true;
            }
        } else if (rolesAndUpperClassesByStam.size() > 1) {
            if (currentUser.getRole().equals(SecurityRole.ADMIN)) {
                return true;
            }
            for (Map.Entry<String, RolesAndUpperClasses> entry : rolesAndUpperClassesByStam.entrySet()) {
                if (currentUser.getAdNumber().equals(registrationVolunteer.getAdNumber())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean hasPermissionToFindUnits() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null;
    }


    public boolean hasPermissionToGetColleagues() {
        User currentUser = userService.getCurrentUser();

        return currentUser != null;
    }

    public boolean hasPermissionToSeeColleagues(User user) {
        User currentUser = userService.getCurrentUser();

        return currentUser != null && currentUser.getStamnummer().equals(user.getStamnummer());
    }


}
