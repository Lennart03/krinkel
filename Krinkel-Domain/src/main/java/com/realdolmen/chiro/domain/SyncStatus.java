package com.realdolmen.chiro.domain;

public enum SyncStatus {
    UNSYNCED("Niet gesynchroniseerd"),
    SYNCED("Gesynchroniseerd");

    private String description;

    private SyncStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

