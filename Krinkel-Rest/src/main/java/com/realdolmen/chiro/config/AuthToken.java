package com.realdolmen.chiro.config;

public class AuthToken {
    private String value;

    public AuthToken(String value) {
        this.value = value;
    }

    public AuthToken() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}