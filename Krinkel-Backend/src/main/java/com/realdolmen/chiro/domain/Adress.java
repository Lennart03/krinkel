package com.realdolmen.chiro.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class Adress implements Serializable {
    @NotBlank
    private String street;
    private int houseNumber;
    @NotNull
    @Max(9999)
    @Min(1000)
    private int postalCode;
    @NotBlank
    private String city;

    public Adress(){}

    public Adress(String street, int houseNumber, int postalCode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
