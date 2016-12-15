package com.realdolmen.chiro.dataholders;

import com.realdolmen.chiro.domain.units.ChiroContact;

import java.util.List;

public class ChiroContactHolder {

    private String adnr;
    private String first_name;
    private String last_name;
    private String afdeling;
    private String gender_id;
    private String birth_date;
    private String street_address;
    private String postal_code;
    private String city;
    private String country;
    private String phone;
    private String email;
    private String id;
    private List<String> functies;

    public ChiroContactHolder() {
    }

    public ChiroContactHolder(ChiroContactHolder chiroContactHolder) {
        this.adnr = chiroContactHolder.getAdnr();
        this.first_name = chiroContactHolder.getFirst_name();
        this.last_name = chiroContactHolder.getLast_name();
        this.afdeling = chiroContactHolder.getAfdeling();
        this.gender_id = chiroContactHolder.getGender_id();
        this.birth_date = chiroContactHolder.getBirth_date();
        this.street_address = chiroContactHolder.getStreet_address();
        this.postal_code = chiroContactHolder.getPostal_code();
        this.city = chiroContactHolder.getCity();
        this.country = chiroContactHolder.getCountry();
        this.phone = chiroContactHolder.getPhone();
        this.email = chiroContactHolder.getEmail();
        this.id = chiroContactHolder.getId();
        this.functies = chiroContactHolder.getFuncties();
    }

    public String getAdnr() {
        return adnr;
    }

    public void setAdnr(String adnr) {
        this.adnr = adnr;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(String afdeling) {
        this.afdeling = afdeling;
    }

    public String getGender_id() {
        return gender_id;
    }

    public void setGender_id(String gender_id) {
        this.gender_id = gender_id;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getFuncties() {
        return functies;
    }

    public void setFuncties(List<String> functies) {
        this.functies = functies;
    }
}

