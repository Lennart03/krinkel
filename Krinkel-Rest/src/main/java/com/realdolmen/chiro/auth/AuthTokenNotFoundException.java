package com.realdolmen.chiro.auth;

public class AuthTokenNotFoundException extends Exception {

    public AuthTokenNotFoundException() {
    }

    public AuthTokenNotFoundException(String message) {
        super(message);
    }
}