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
import java.util.Calendar;
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

    @Email
    private String emailSubscriber;

    @Embedded
    @Valid
    @NotNull
    private Address address;

    @Temporal(TemporalType.DATE)
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastChange;

    @NotBlank
    private String stamnumber;

    /**
     * this will contain the original stamnummer if it is changed to other during saving.
     * see the comment in the participant service for more info
     * */
    private String originalStamNumber;

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

    @Size(max = 256)
    private String remarksFood;
    private boolean socialPromotion = false;
    @Size(max = 256)
    private String medicalRemarks;
    @Size(max = 256)
    private String remarks;

    @Enumerated(EnumType.STRING)
    private Status status = Status.TO_BE_PAID;


    @Enumerated(EnumType.STRING)
    private SyncStatus syncStatus = SyncStatus.UNSYNCED;

    @Transient
    private String httpStatus;



    /**
     * Only numbers, spaces and optionally a '+' sign are allowed.
     * Front end does more thorough checking.
     **/
    @Pattern(regexp = "^\\+*[0-9\\s]*$")
    private String phoneNumber;

    public RegistrationParticipant() {
    }

    public RegistrationParticipant(String adNumber, String email, String firstName, String lastName, Date birthdate, String stamnumber, Gender gender, EventRole eventRole, Eatinghabbit eatinghabbit, String emailSubscriber) {
        this.adNumber = adNumber;
        this.email = email;
        this.emailSubscriber = email;
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
        this.emailSubscriber = builder.emailSubscriber;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthdate = builder.birthdate;
        this.lastChange = builder.lastChange;
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
        this.language = builder.language;
    }

    public String getOriginalStamNumber() {
        return originalStamNumber;
    }

    public void setOriginalStamNumber(String originalStamNumber) {
        this.originalStamNumber = originalStamNumber;
    }

    public boolean isRegisteredByOther() {
        return !this.getAdNumber().equals(this.registeredBy);
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

    public String getEmailSubscriber() {
        return emailSubscriber;
    }

    public void setEmailSubscriber(String emailSubscriber) {
        this.emailSubscriber = emailSubscriber;
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

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Date updateLastChange() {
        this.lastChange = Calendar.getInstance().getTime();
        return this.lastChange;
    }

    public static class RegistrationParticipantBuilder {
        private String adNumber;
        private String firstName;
        private String lastName;
        private String email;
        private String emailSubscriber;
        private Address address;
        private Date birthdate;
        private Date lastChange;
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

        public RegistrationParticipant build() {
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

        public RegistrationParticipantBuilder emailSubscriber(String emailSubscriber) {
            this.emailSubscriber = emailSubscriber;
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

        public RegistrationParticipantBuilder lastChange(Date lastChange) {
            this.lastChange = lastChange;
            return this;
        }
    }
}

