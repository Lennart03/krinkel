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
        NATIONAL_CAMPGROUND("Aanbod nationale kampgrond"), // Aanbod nationale kampgrond
        CAMPGROUND("Kampgrondtrekker"), // Kampgrondtrekker
        KRINKEL_EDITORIAL("Klinkerredactie"), // Klinkerredactie
        COOCKING("Kookploeg"), // Kookploeg

        LOGISTICS_CAMPGROUND("Logistiek (kampgrond)"), // Logistiek (kampgrond)
        LOGISTICS_NATIONAL("Logistiek (nationaal)"), // Logistiek (nationaal)

        LIVING_GROUP_GUIDANCE("Leefgroepbegeleiding"), // Leefgroepbegeleiding
        CUSTOM("custom"); // I didn't select something from this list but instead defined my own function in the 'other' field.

        private String description;

        Preset(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }


    @Enumerated(EnumType.STRING)
    private Preset preset = null;

    /**
     * Custom defined
     */
    private String other = null;

    public VolunteerFunction() {
    }

    public VolunteerFunction(Preset preset) {
        this.preset = preset;
    }

    /**
     * Sets the preset to Preset.CUSTOM and sets the 'other' field.
     *
     * @param other A custom defined function.
     */
    public VolunteerFunction(String other) {
        this.preset = Preset.CUSTOM;
        this.other = other;
    }

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
