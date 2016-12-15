package com.realdolmen.chiro.domain.dto;

import com.realdolmen.chiro.domain.User;

public class UserDTO {

    private String email, firstName, lastName, adNumber;

    public UserDTO(User user) {
        email = user.getEmail();
        firstName = user.getFirstname();
        lastName = user.getLastname();
        adNumber = user.getAdNumber();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(String adNumber) {
        this.adNumber = adNumber;
    }
}
