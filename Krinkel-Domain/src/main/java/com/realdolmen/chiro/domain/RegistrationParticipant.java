package com.realdolmen.chiro.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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

    private String registeredBy;

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
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @NotBlank
    private String stamnumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender = Gender.X;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EventRole eventRole;

    private boolean buddy = false;

    @ElementCollection(fetch = FetchType.EAGER)
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
     * Only numbers, spaces and optionally a '+' sign are allowed.
     * Front end does more thorough checking.
     **/
    @Pattern(regexp = "^\\+*[0-9\\s]*$")
    private String phoneNumber;

    public RegistrationParticipant() {}

    public RegistrationParticipant(String adNumber, String email, String firstName, String lastName, Date birthdate, String stamnumber, Gender gender, EventRole eventRole, Eatinghabbit eatinghabbit) {
        this.adNumber = adNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.stamnumber = stamnumber;
        this.gender = gender;
        this.eventRole = eventRole;
        this.eatinghabbit = eatinghabbit;
    }

    private RegistrationParticipant(RegistrationParticipant.RegistrationParticipantBuilder builder) {
        this.adNumber = builder.adNumber;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthdate = builder.birthdate;
        this.stamnumber = builder.stamnumber;
        this.gender = builder.gender;
        this.eventRole = builder.eventRole;
        this.eatinghabbit = builder.eatinghabbit;
        this.address = builder.address;
        this.buddy = builder.buddy;
        this.status = builder.status;
        this.socialPromotion = builder.socialPromotion;
        this.remarksFood = builder.remarksFood;
        this.medicalRemarks = builder.medicalRemarks;
        this.remarks = builder.remarks;
        this.phoneNumber = builder.phoneNumber;
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

    public EventRole getEventRole() {
        return eventRole;
    }

    public void setEventRole(EventRole eventRole) {
        this.eventRole = eventRole;
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

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public static class RegistrationParticipantBuilder {
        private String adNumber;
        private String firstName;
        private String lastName;
        private String email;
        private Address address;
        private Date birthdate;
        private String stamnumber;
        private Gender gender = Gender.X;
        private EventRole eventRole;
        private boolean buddy = false;
        private List<Language> language = new ArrayList<>();
        private Eatinghabbit eatinghabbit;
        private String remarksFood;
        private boolean socialPromotion = false;
        private String medicalRemarks;
        private String remarks;
        private Status status = Status.TO_BE_PAID;
        private String phoneNumber;

        public RegistrationParticipant build(){
            return new RegistrationParticipant(this);
        }

        public RegistrationParticipantBuilder adNumber(String adNumber) {
            this.adNumber = adNumber;
            return this;
        }

        public RegistrationParticipantBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public RegistrationParticipantBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public RegistrationParticipantBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegistrationParticipantBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public RegistrationParticipantBuilder birthdate(Date birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public RegistrationParticipantBuilder stamnumber(String stamnumber) {
            this.stamnumber = stamnumber;
            return this;
        }

        public RegistrationParticipantBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public RegistrationParticipantBuilder role(EventRole eventRole) {
            this.eventRole = eventRole;
            return this;
        }

        public RegistrationParticipantBuilder buddy(boolean buddy) {
            this.buddy = buddy;
            return this;
        }

        public RegistrationParticipantBuilder language(List<Language> language) {
            this.language = language;
            return this;
        }

        public RegistrationParticipantBuilder eatinghabbit(Eatinghabbit eatinghabbit) {
            this.eatinghabbit = eatinghabbit;
            return this;
        }

        public RegistrationParticipantBuilder remarksFood(String remarksFood) {
            this.remarksFood = remarksFood;
            return this;
        }

        public RegistrationParticipantBuilder socialPromotion(boolean socialPromotion) {
            this.socialPromotion = socialPromotion;
            return this;
        }

        public RegistrationParticipantBuilder medicalRemarks(String medicalRemarks) {
            this.medicalRemarks = medicalRemarks;
            return this;
        }

        public RegistrationParticipantBuilder remarks(String remarks) {
            this.remarks = remarks;
            return this;
        }

        public RegistrationParticipantBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public RegistrationParticipantBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
    }
}

