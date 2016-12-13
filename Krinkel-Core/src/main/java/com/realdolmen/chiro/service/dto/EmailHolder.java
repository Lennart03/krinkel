package com.realdolmen.chiro.service.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class EmailHolder {

    @Email
    @NotBlank
    private String email;

    public EmailHolder(String email) {
        this.email = email;
    }

    public EmailHolder() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
