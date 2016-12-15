package com.realdolmen.chiro.domain.units;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Chiro Units.
 * <p>
 * Can represent a ChiroGroup, GewestUnit, or VerbondUnit.
 */
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "stamnummer"
)*/
public class ChiroUnit {
    /**
     * Normalized StamNummer number of a groep, gewest or verbond (without '/' and without whitespace)
     */
    @JsonProperty("stamnummer")
    private String stamNummer;

    @JsonProperty("naam")
    private String naam;

    @JsonProperty("bovenliggende_stamnummer")
    @JsonIgnoreProperties({"bovenliggende_stamnummer", "onderliggende_stamnummers"})
    private ChiroUnit upper;

    @JsonProperty("onderliggende_stamnummers")
    @JsonIgnoreProperties({"bovenliggende_stamnummer", "onderliggende_stamnummers"})
    private List<ChiroUnit> lower = new ArrayList<>();

    @JsonProperty("aantal_ingeschreven_deelnemers")
    private int participantsCount = 0;

    @JsonProperty("aantal_ingeschreven_vrijwilligers")
    private int volunteersCount = 0;

    public ChiroUnit() {
        this.lower = new ArrayList<>();
    }

    public ChiroUnit(String stam, String name) {
        this();
        this.stamNummer = trim(stam);
        this.naam = name;
    }

    public String getStamNummer() {
        return stamNummer;
    }

    public void setStamNummer(String stamNummer) {
        this.stamNummer = stamNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public int getVolunteersCount() {
        return volunteersCount;
    }

    public void setVolunteersCount(int volunteersCount) {
        this.volunteersCount = volunteersCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((naam == null) ? 0 : naam.hashCode());
        result = prime * result + ((stamNummer == null) ? 0 : stamNummer.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChiroUnit other = (ChiroUnit) obj;
        if (naam == null) {
            if (other.naam != null)
                return false;
        } else if (!naam.equals(other.naam))
            return false;
        if (stamNummer == null) {
            if (other.stamNummer != null)
                return false;
        } else if (!stamNummer.equals(other.stamNummer))
            return false;
        return true;
    }

    private String trim(String stam) {
        if (stam == null) {
            return "";
        } else {
            return stam.replace("/", "")
                    .replace("\\s", "")
                    .replace(" ", "");
        }
    }


}
