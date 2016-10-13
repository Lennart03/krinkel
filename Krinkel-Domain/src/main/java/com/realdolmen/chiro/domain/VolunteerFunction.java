package com.realdolmen.chiro.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Functie/Function of a Medewerker/Volunteer.
 * Can be selected from a preset of values.
 * Can be manually inputted during registration.
 */
@Embeddable
public class VolunteerFunction {

    public enum Preset {
        NATIONAL_CAMPGROUND, // Aanbod nationale kampgrond
        CAMPGROUND, // Kampgrondtrekker
        KLINKER_EDITORIAL, // Klinkerredactie
        COOCKING, // Kookploeg

        LOGISTICS_CAMPGROUND, // Logistiek (kampgrond)
        LOGISTICS_NATIONAL, // Logistiek (nationaal)

        LIVING_GROUP_GUIDANCE, // Leefgroepbegeleiding
        CUSTOM // I didn't select something from this list but instead defined my own function in the 'other' field.
    }

    public VolunteerFunction(){}

    public VolunteerFunction(Preset preset){
        this.preset = preset;
    }

    /**
     * Sets the preset to Preset.CUSTOM and sets the 'other' field.
     *
     * @param other A custom defined function.
     */
    public VolunteerFunction(String other){
        this.preset = Preset.CUSTOM;
        this.other = other;
    }

    @Enumerated(EnumType.STRING)
    private Preset preset = null;

    /**
     * Custom defined
     */
    private String other = null;

    public Preset getPreset() {
        return preset;
    }

    public void setPreset(Preset preset) {
        this.preset = preset;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}