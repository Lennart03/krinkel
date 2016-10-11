package com.realdolmen.chiro.domain;

public class ChiroGewestUnit {

    private String stamNumber;

    private String name;

    public ChiroGewestUnit(String stamNumber, String name) {
        this.stamNumber = stamNumber;
        this.name = name;
    }

    public String getStamNumber() {
        return stamNumber;
    }

    public void setStamNumber(String stamNumber) {
        this.stamNumber = stamNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
