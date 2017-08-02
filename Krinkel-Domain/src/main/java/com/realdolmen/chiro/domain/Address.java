package com.realdolmen.chiro.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Country is always assumed to be Belgium.
 * <p>
 * While a small portion of members are from other countries,
 * these do not use the registration form provided by this app.
 * These are handled by the administration itself.
 */
@Embeddable
public class Address implements Serializable {
    /**
     * Longest street name in Belgium is:
     * "Burgemeester Charles Rotsart de Hertainglaan" - 44 characters
     */
    @NotBlank
    @Size(max = 60)
    private String street;

    /**
     * Not necessary a strict numeric value.
     * Examples include:
     * "42", "7B" etc.
     */
    @NotBlank
    private String houseNumber;

    @Max(9999)
    @Min(1000)
    private int postalCode;

    /**
     * The city in Belgium with the longest name is:
     * 'Nil-Saint-Vincent-Saint-Martin'(30 characters in total)
     */
    @NotBlank
    @Size(max = 50)
    private String city;

    public Address(String street, String houseNumber, int postalCode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Address() {
        // Default constructor for management by Container.
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isComplete() {
        return street != null && !street.isEmpty() && houseNumber != null && !houseNumber.isEmpty() && postalCode > 1000 && postalCode < 9999 && city != null && !city.isEmpty();
    }

    @Override
    public String toString(){
        return street + " " + houseNumber + " " + postalCode + " " + city;
    }
}
