package com.realdolmen.chiro.domain;

public enum SecurityRole {
    ADMIN(4),
    NATIONAAL(3),
    VERBOND(2),
    GEWEST(1),
    GROEP(0);

    private int value;

    private SecurityRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
