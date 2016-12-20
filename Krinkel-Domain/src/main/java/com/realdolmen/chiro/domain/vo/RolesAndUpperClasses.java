package com.realdolmen.chiro.domain.vo;

import com.realdolmen.chiro.domain.SecurityRole;

public class RolesAndUpperClasses {
    private SecurityRole securityRole;
    private String stamNumberUpperUnit;

    public RolesAndUpperClasses(SecurityRole securityRole, String stamNumberUpperUnit) {
        this.securityRole = securityRole;
        this.stamNumberUpperUnit = stamNumberUpperUnit;
    }

    public SecurityRole getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(SecurityRole securityRole) {
        this.securityRole = securityRole;
    }

    public String getStamNumberUpperUnit() {
        return stamNumberUpperUnit;
    }

    public void setStamNumberUpperUnit(String stamNumberUpperUnit) {
        this.stamNumberUpperUnit = stamNumberUpperUnit;
    }
}
