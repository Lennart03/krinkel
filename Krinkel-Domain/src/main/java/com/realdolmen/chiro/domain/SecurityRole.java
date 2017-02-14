package com.realdolmen.chiro.domain;

public enum SecurityRole {
    ADMIN(4),
    NATIONAAL(3),
    VERBOND(2),
    GEWEST(1),
    GROEP(0);

    private int value;

    SecurityRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString(){
        switch (getValue()){
            case 4 : return "admin";
            case 3 : return "nationaal";
            case 2 : return "verbond";
            case 1 : return "gewest";
            case 0 : return "groep";
            default : return "INVALID";
        }
    }
}
