package com.realdolmen.chiro.domain.units;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groepen")
public class RawChiroUnit {

//    @Column(name = "groep_stamnr")
    @Id
    private String groepStamNummer;

//    @Column(name = "groep_naam")
    private String groepNaam;

//    @Column(name = "gewest_stamnr")
    private String gewestStamNummer;

//    @Column(name = "gewest_naam")
    private String gewestNaam;

//    @Column(name = "verbond_stamnr")
    private String verbondStamNummer;

//    @Column(name = "verbond_naam")
    private String verbondNaam;

    public RawChiroUnit() {
    }

    public String getGroepNaam() {
        return groepNaam;
    }

    public void setGroepNaam(String groepNaam) {
        this.groepNaam = groepNaam;
    }

    public String getGroepStamNummer() {
        return groepStamNummer;
    }

    public void setGroepStamNummer(String groepStamNummer) {
        this.groepStamNummer = groepStamNummer;
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
}
