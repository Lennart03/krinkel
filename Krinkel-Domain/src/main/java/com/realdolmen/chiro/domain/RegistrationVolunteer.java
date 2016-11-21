package com.realdolmen.chiro.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class RegistrationVolunteer extends RegistrationParticipant {
    /**
     * 'Kampgrond'
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private CampGround campGround;

    @Embedded
    @NotNull
    private VolunteerFunction function;

    /**
     * Zero or many PreCamp moments.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private List<PreCamp> preCampList = new ArrayList<>();

    /**
     * Zero or many PostCamp moments.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private List<PostCamp> postCampList = new ArrayList<>();

    public RegistrationVolunteer() {
    }

    public RegistrationVolunteer(String adNumber, String email, String firstName, String lastName, Date birthdate, String stamnumber, Gender gender, EventRole eventRole, Eatinghabbit eatinghabbit, CampGround campGround, VolunteerFunction function, String emailSubscriber) {
        super(adNumber, email, firstName, lastName, birthdate, stamnumber, gender, eventRole, eatinghabbit, emailSubscriber);
        this.campGround = campGround;
        this.function = function;
    }

    public CampGround getCampGround() {
        return campGround;
    }

    public void setCampGround(CampGround campGround) {
        this.campGround = campGround;
    }

    public VolunteerFunction getFunction() {
        return function;
    }

    public void setFunction(VolunteerFunction function) {
        this.function = function;
    }

    public List<PreCamp> getPreCampList() {
        return preCampList;
    }

    public void setPreCampList(List<PreCamp> preCampList) {
        this.preCampList = preCampList;
    }

    public List<PostCamp> getPostCampList() {
        return postCampList;
    }

    public void setPostCampList(List<PostCamp> postCampList) {
        this.postCampList = postCampList;
    }

    public void addPostCamp(PostCamp postCamp) {
        this.postCampList.add(postCamp);
    }

    public void addPreCamp(PreCamp preCamp) {
        this.preCampList.add(preCamp);
    }
}
