package com.realdolmen.chiro.domain;



public class User {

    private SecurityRole role;
    private String adNumber;
    private String email;
    private String firstname;
    private String lastname;
    private boolean registered;
    private boolean hasPaid;
    private String stamnummer;
    private String username;

    public User() {
    }

    public User(String firstName, SecurityRole role, String adNumber, String email, String firstname, String lastname) {
        this.role = role;
        this.adNumber = adNumber;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(String adNumber, String email, String firstname, String lastname, String stamnummer) {
        this.adNumber = adNumber;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.stamnummer = stamnummer;
    }

    public String getStamnummer() {
        return stamnummer;
    }

    public void setStamnummer(String stamnummer) {
        this.stamnummer = stamnummer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SecurityRole getRole() {
        return role;
    }

    public void setRole(SecurityRole role) {
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

    public boolean isRegistered() {
        return registered;
    }

    public boolean getIsRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public boolean hasPaid() {
        return hasPaid;
    }

    public boolean getHasPaid(){
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public String getNormalizedStamNumber(){
        
        return this.getStamnummer().replace("/", "")
                                   .replace(" ", "");

    }
}
