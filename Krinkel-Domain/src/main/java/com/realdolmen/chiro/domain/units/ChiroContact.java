package com.realdolmen.chiro.domain.units;

import com.realdolmen.chiro.domain.Gender;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by LVDBB73 on 15/12/2016.
 */
public class ChiroContact {

    private String adnr;
    private String firstName;
    private String lastName;
    private String afdeling;
    private List<String> functies;
    private Gender gender;
    private Date birthDate;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String country;
    private String phone;
    private String email;
    private String id;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ChiroContact() {

    }

    public ChiroContact(ChiroContact chiroContact) {
        this.adnr = chiroContact.getAdnr();
        this.firstName = chiroContact.getFirstName();
        this.lastName = chiroContact.getLastName();
        this.afdeling = chiroContact.getAfdeling();
        this.functies = chiroContact.getFuncties();
        this.gender = chiroContact.getGender();
        this.birthDate = chiroContact.getBirthDate();
        this.streetAddress = chiroContact.getStreetAddress();
        this.postalCode = chiroContact.getPostalCode();
        this.city = chiroContact.getCity();
        this.country = chiroContact.getCountry();
        this.phone = chiroContact.getPhone();
        this.email = chiroContact.getEmail();
        this.id = chiroContact.getId();
    }

    public String getAdnr() {
        return adnr;
    }

    public void setAdnr(String adnr) {
        this.adnr = adnr;
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

    public String getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(String afdeling) {
        this.afdeling = afdeling;
    }

    public List<String> getFuncties() {
        return functies;
    }

    public void setFuncties(List<String> functies) {
        this.functies = functies;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender(String gender) {
        if (gender == null) {
            this.gender = Gender.X;
        } else {
            this.gender = Gender.values()[Integer.parseInt(gender)];
        }
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setBirthDate(String birthDate) {
        if (birthDate.equals("")) {
            this.birthDate = null;
        } else {
            try {
                this.birthDate = dateFormat.parse(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    @Override
    public String toString() {
        return "ChiroContact{" +
                "adnr='" + adnr + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", afdeling='" + afdeling + '\'' +
                ", functies=" + functies +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", streetAddress='" + streetAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
