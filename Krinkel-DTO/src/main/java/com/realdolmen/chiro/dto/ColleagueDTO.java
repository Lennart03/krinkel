package com.realdolmen.chiro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.realdolmen.chiro.conversion.GenderDeserializer;
import com.realdolmen.chiro.conversion.GenderSerializer;
import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.Gender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ColleagueDTO {

    @JsonProperty("adnr")
    private String adNumber;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    //afdeling
    @JsonProperty("afdeling")
    private String division;

    @JsonProperty("functies")
    private List<String> functions;

    @JsonProperty("gender_id")
    @JsonSerialize(using = GenderSerializer.class)
    @JsonDeserialize(using = GenderDeserializer.class)
    private Gender gender;

    @JsonProperty("birth_date")
    private Date birthDate;

    private String street_address;

    private String postal_code;

    private String city;

    private String country;

    private String phone;

    private String email;

    public ColleagueDTO() {

    }


    public String getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(String adNumber) {
        this.adNumber = adNumber;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColleagueDTO that = (ColleagueDTO) o;

        return adNumber != null ? adNumber.equals(that.adNumber) : that.adNumber == null;
    }

    @Override
    public int hashCode() {
        return adNumber != null ? adNumber.hashCode() : 0;
    }
}
