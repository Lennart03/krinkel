package com.realdolmen.chiro.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class RegistrationParticipant {
    @GeneratedValue @Id
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String adNumber;

    @Size(min=2)
    private String firstName;

    @Size(min=2)
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @NotBlank
    private String stamnumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Function function;

    private boolean buddy = false;

    @ElementCollection
    private List<Language> language=new ArrayList<Language>();

    @Enumerated(EnumType.STRING)
    private Eatinghabbit eatinghabbit;

    private String remarksFood;
    private boolean socialPromotion = false;
    private String medicalRemarks;
    private String remarks;


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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getStamnumber() {
        return stamnumber;
    }

    public void setStamnumber(String stamnumber) {
        this.stamnumber = stamnumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public boolean isBuddy() {
        return buddy;
    }

    public void setBuddy(boolean buddy) {
        this.buddy = buddy;
    }

    public List<Language> getLanguage() {
        return language;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }

    public Eatinghabbit getEatinghabbit() {
        return eatinghabbit;
    }

    public void setEatinghabbit(Eatinghabbit eatinghabbit) {
        this.eatinghabbit = eatinghabbit;
    }

    public String getRemarksFood() {
        return remarksFood;
    }

    public void setRemarksFood(String remarksFood) {
        this.remarksFood = remarksFood;
    }

    public boolean isSocialPromotion() {
        return socialPromotion;
    }

    public void setSocialPromotion(boolean socialPromotion) {
        this.socialPromotion = socialPromotion;
    }

    public String getMedicalRemarks() {
        return medicalRemarks;
    }

    public void setMedicalRemarks(String medicalRemarks) {
        this.medicalRemarks = medicalRemarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(String adNumber) {
        this.adNumber = adNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

