package com.realdolmen.chiro.domain;


import java.util.Date;

public class User {

    private String username;
    private String password;
    private String role;
    private String adNumber;
    private String token;
    private Boolean subscribed;
    private String email;
    private String firstname;
    private String lastname;
    private Date birthDate;
    private String streetname;
    private Integer houseNumber;
    private Integer postalCode;
    private String city;
    private String phoneNumber;
    private String unitStamNummer;


    public User(String username, String password, String role, String adNumber, String token, Boolean subscribed, String unitStamNummer) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.adNumber = adNumber;
        this.token = token;
        this.subscribed = subscribed;
        this.unitStamNummer = unitStamNummer;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUnitStamNummer() {
        return unitStamNummer;
    }

    public void setUnitStamNummer(String unitStamNummer) {
        this.unitStamNummer = unitStamNummer;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
