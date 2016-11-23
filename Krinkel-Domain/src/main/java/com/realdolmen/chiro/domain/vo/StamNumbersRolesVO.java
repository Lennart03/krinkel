package com.realdolmen.chiro.domain.vo;

import java.util.Map;
import java.util.TreeMap;

public class StamNumbersRolesVO {
    String stamNumber;
    private Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam = new TreeMap<>();

    public String getStamNumber() {
        return stamNumber;
    }

    public void setStamNumber(String stamNumber) {
        this.stamNumber = stamNumber;
    }

    public Map<String, RolesAndUpperClasses> getRolesAndUpperClassesByStam() {
        return rolesAndUpperClassesByStam;
    }

    public void setRolesAndUpperClassesByStam(Map<String, RolesAndUpperClasses> rolesAndUpperClassesByStam) {
        this.rolesAndUpperClassesByStam = rolesAndUpperClassesByStam;
    }
}
