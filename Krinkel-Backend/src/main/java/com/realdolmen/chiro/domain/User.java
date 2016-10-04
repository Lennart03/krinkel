package com.realdolmen.chiro.domain;


public class User {

    private String username;
    private String password;
    private String role;
    private String adNumber;
    private String token;
    private Boolean subscribed;

    public User(String username, String password, String role, String adNumber, String token, Boolean subscribed) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.adNumber = adNumber;
        this.token = token;
        this.subscribed = subscribed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(String adNumber) {
        this.adNumber = adNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }
}
