package com.realdolmen.chiro.domain;

public enum Status {
    TO_BE_PAID("Te betalen"),
    PAID("Betaald"),
    CONFIRMED("Bevestigd"),
    CANCELLED("Geannuleerd");
    //Status changes to confirmed when the confirmation link is clicked

    private String description;

    private Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
