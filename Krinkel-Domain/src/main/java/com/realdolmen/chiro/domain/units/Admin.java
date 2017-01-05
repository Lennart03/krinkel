package com.realdolmen.chiro.domain.units;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by LVDBB73 on 8/12/2016.
 */
@Entity
@Table(name = "admins")
@DiscriminatorColumn(name="ADMIN_TYPE")
@DiscriminatorValue("admin")
public class Admin {

    @Id
    @Column(name = "adnr")
    private Integer adNumber;

    @Column(name = "email")
    @Email
    @NotBlank
    private String email;

    @Column(name = "firstname")
    @NotBlank
    private String firstname;

    @Column(name = "lastname")
    @NotBlank
    private String lastname;

    public Integer getAdNumber() {
        return adNumber;
    }

    public Admin() {
    }

    public Admin(Integer adNumber, String email, String firstname, String lastname) {
        this.adNumber = adNumber;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setAdNumber(Integer adNumber) {
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
