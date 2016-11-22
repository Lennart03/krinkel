package com.realdolmen.chiro.domain.vo;

import com.realdolmen.chiro.domain.SecurityRole;

public class SecurityStamNumberVO {
    private SecurityRole HighestRole;
    private String HighestStamNumber;

    public SecurityStamNumberVO() {
    }

    public SecurityStamNumberVO(SecurityRole highestRole, String highestStamNumber) {
        HighestRole = highestRole;
        HighestStamNumber = highestStamNumber;
    }

    public SecurityRole getHighestRole() {
        return HighestRole;
    }

    public void setHighestRole(SecurityRole highestRole) {
        HighestRole = highestRole;
    }

    public String getHighestStamNumber() {
        return HighestStamNumber;
    }

    public void setHighestStamNumber(String highestStamNumber) {
        HighestStamNumber = highestStamNumber;
    }
}
