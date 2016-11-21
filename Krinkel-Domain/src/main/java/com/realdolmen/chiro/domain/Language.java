package com.realdolmen.chiro.domain;

public enum Language {
    SPANISH("Spaans"),
    FRENCH("Frans"),
    ENGLISH("Engels");

    private String description;

    private Language(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
