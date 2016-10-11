package com.realdolmen.chiro.domain.units;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Chiro Units.
 */
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "stam"
)
public class ChiroUnit {
    private String stam;

    private String name;

    @JsonProperty("boven_liggende")
    @JsonIdentityReference(alwaysAsId = true)
    private ChiroUnit upper;

    @JsonProperty("onder_liggende")
    @JsonIdentityReference(alwaysAsId = true)
    private List<ChiroUnit> lower = new ArrayList<>();

    public ChiroUnit() {
        this.lower = new ArrayList<>();
    }

    public ChiroUnit(String stam, String name) {
        this();
        this.stam = stam;
        this.name = name;
    }

    public String getStam() {
        return stam;
    }

    public void setStam(String stam) {
        this.stam = stam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChiroUnit getUpper() {
        return upper;
    }

    public void setUpper(ChiroUnit upper) {
        this.upper = upper;
    }

    public List<ChiroUnit> getLower() {
        return lower;
    }

    public void setLower(List<ChiroUnit> lower) {
        this.lower = lower;
    }
}
