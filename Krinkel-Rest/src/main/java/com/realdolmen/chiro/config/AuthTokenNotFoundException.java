package com.realdolmen.chiro.config;

public class AuthTokenNotFoundException extends Exception{

    public AuthTokenNotFoundException() {
    }

    public AuthTokenNotFoundException(String message) {
        super(message);
    }
}