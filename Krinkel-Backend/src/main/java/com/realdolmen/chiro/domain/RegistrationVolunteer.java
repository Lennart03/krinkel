package com.realdolmen.chiro.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class RegistrationVolunteer extends RegistrationParticipant {
    /**
     * 'Kampgrond'
     */
    @Enumerated
    private CampGround campGround;

    @Embedded
    private VolunteerFunction function;

    /**
     * Zero or many PreCamp moments.
     */
    @OneToMany
    private List<PreCamp> preCampList = new ArrayList<>();

    /**
     * Zero or many PostCamp moments.
     */
    @OneToMany
    private List<PostCamp> postCampList = new ArrayList<>();

    public RegistrationVolunteer(String adNumber, String firstName, String lastName, Date birthdate, String stamnumber, Gender gender, Role role, Eatinghabbit eatinghabbit, CampGround campGround, VolunteerFunction function) {
        super(adNumber, firstName, lastName, birthdate, stamnumber, gender, role, eatinghabbit);
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
}
