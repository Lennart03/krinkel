package com.realdolmen.chiro.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RegistrationParticipant {
    @GeneratedValue
    @Id
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String adNumber;

    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Embedded
    @Valid
    @NotNull
    private Address address;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @NotBlank
    private String stamnumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender = Gender.X;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    private boolean buddy = false;

    @ElementCollection
    private List<Language> language = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Eatinghabbit eatinghabbit;

    private String remarksFood;
    private boolean socialPromotion = false;
    private String medicalRemarks;
    private String remarks;

    @Enumerated(EnumType.STRING)
    private Status status = Status.TO_BE_PAID;

    /**
     * Regex adapted from frontend validation.
     */
    @Pattern(regexp = "^((\\+|00)32\\s?|0)(\\d\\s?\\d{3}|\\d{2}\\s?\\d{2})(\\s?\\d{2}){2}|((\\+|00)32\\s?|0)4(60|[789]\\d)(\\s?\\d{2}){3}$")
    private String phoneNumber;

    public RegistrationParticipant() {
    }

    public RegistrationParticipant(String adNumber, String email, String firstName, String lastName, Date birthdate, String stamnumber, Gender gender, Role role, Eatinghabbit eatinghabbit) {
        this.adNumber = adNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.stamnumber = stamnumber;
        this.gender = gender;
        this.role = role;
        this.eatinghabbit = eatinghabbit;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

