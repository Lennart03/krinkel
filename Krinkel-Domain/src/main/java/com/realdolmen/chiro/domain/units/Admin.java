package com.realdolmen.chiro.domain.units;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by LVDBB73 on 8/12/2016.
 */
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @Column(name = "adnr")
    private Integer adNummer;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    public Integer getAdNummer() {
        return adNummer;
    }

    public Admin() {
    }

    public Admin(Integer adNummer, String email, String firstname, String lastname) {
        this.adNummer = adNummer;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setAdNummer(Integer adNummer) {
        this.adNummer = adNummer;
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
