package com.realdolmen.chiro.domain;


import java.util.Date;

public class User {

    private String username;
    private Role role;
    private String adNumber;
    private String email;
    private String firstname;
    private String lastname;

    public User() {
    }

    public User(String username, Role role, String adNumber, String email, String firstname, String lastname) {
        this.username = username;
        this.role = role;
        this.adNumber = adNumber;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(String adNumber) {
        this.adNumber = adNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


}
