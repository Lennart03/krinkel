package com.realdolmen.chiro.domain.units;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for Chiro Units.
 * <p>
 * Can represent a ChiroGroup, GewestUnit, or VerbondUnit.
 */
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "stamnummer"
)*/
public class ChiroGroepGewestVerbond {

    @JsonProperty("groepstamnummer")
    private String groepStamNummer;

    @JsonProperty("groepnaam")
    private String groepNaam;

    @JsonProperty("geweststamnummer")
    private String gewestStamNummer;

    @JsonProperty("gewestnaam")
    private String gewestNaam;

    @JsonProperty("verbondstamnummer")
    private String verbondStamNummer;

    @JsonProperty("verbondnaam")
    private String verbondNaam;


    public ChiroGroepGewestVerbond() {

    }

    public ChiroGroepGewestVerbond(String groepStamNummer, String groepNaam, String gewestStamNummer, String gewestNaam, String verbondStamNummer, String verbondNaam) {
        this.groepStamNummer = groepStamNummer;
        this.groepNaam = groepNaam;
        this.gewestStamNummer = gewestStamNummer;
        this.gewestNaam = gewestNaam;
        this.verbondStamNummer = verbondStamNummer;
        this.verbondNaam = verbondNaam;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groepNaam == null) ? 0 : groepNaam.hashCode());
        result = prime * result + ((groepStamNummer == null) ? 0 : groepStamNummer.hashCode());
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
        ChiroGroepGewestVerbond other = (ChiroGroepGewestVerbond) obj;
        if (groepNaam == null) {
            if (other.groepNaam != null)
                return false;
        } else if (!groepNaam.equals(other.groepNaam))
            return false;
        if (groepStamNummer == null) {
            if (other.groepStamNummer != null)
                return false;
        } else if (!groepStamNummer.equals(other.groepStamNummer))
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

    public String getGroepStamNummer() {
        return groepStamNummer;
    }

    public void setGroepStamNummer(String groepStamNummer) {
        this.groepStamNummer = groepStamNummer;
    }

    public String getGroepNaam() {
        return groepNaam;
    }

    public void setGroepNaam(String groepNaam) {
        this.groepNaam = groepNaam;
    }

    public String getGewestStamNummer() {
        return gewestStamNummer;
    }

    public void setGewestStamNummer(String gewestStamNummer) {
        this.gewestStamNummer = gewestStamNummer;
    }

    public String getGewestNaam() {
        return gewestNaam;
    }

    public void setGewestNaam(String gewestNaam) {
        this.gewestNaam = gewestNaam;
    }

    public String getVerbondStamNummer() {
        return verbondStamNummer;
    }

    public void setVerbondStamNummer(String verbondStamNummer) {
        this.verbondStamNummer = verbondStamNummer;
    }

    public String getVerbondNaam() {
        return verbondNaam;
    }

    public void setVerbondNaam(String verbondNaam) {
        this.verbondNaam = verbondNaam;
    }

    @Override
    public String toString(){
        return "ChiroGroepGewestVerbond: Groep " + groepStamNummer + " " + groepNaam+", Gewest " + gewestStamNummer + " " + gewestNaam + ", Verbond " + verbondStamNummer + " " + verbondNaam;
    }

}
