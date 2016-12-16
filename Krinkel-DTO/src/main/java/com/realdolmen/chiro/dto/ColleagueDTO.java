package com.realdolmen.chiro.dto;

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
    private List<String> functions = new ArrayList<>();

    @JsonProperty("gender_id")
    @JsonSerialize(using = GenderSerializer.class)
    @JsonDeserialize(using = GenderDeserializer.class)
    private Gender gender;

    private Date birthDate;

    private Address address;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
}
