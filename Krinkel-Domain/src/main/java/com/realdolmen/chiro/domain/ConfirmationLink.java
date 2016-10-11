package com.realdolmen.chiro.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ConfirmationLink {
    @Id
    @GeneratedValue
    private Long id;

    private String adNumber;

    private String token;

    public ConfirmationLink() {
    }

    public ConfirmationLink(String adNumber, String token) {
        this.adNumber = adNumber;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
