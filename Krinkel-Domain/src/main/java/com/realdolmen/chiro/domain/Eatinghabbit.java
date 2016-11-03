package com.realdolmen.chiro.domain;

public enum Eatinghabbit {
    VEGI("Vegetarisch"),
    HALAL("Halal"),
    FISHANDMEAT("Vis en vlees");

    private String description;

    private Eatinghabbit(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
