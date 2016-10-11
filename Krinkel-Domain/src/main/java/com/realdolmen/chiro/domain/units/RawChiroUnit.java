package com.realdolmen.chiro.domain.units;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="groepen")
public class RawChiroUnit {

    @Column(name="groep_stamnr")
    @Id
    private String stamNumber;

    @Column(name="groep_naam")
    private String name;

    @Column(name="gewest_stamnr")
    private String gewest;

    @Column(name="gewest_naam")
    private String gewestName;

    @Column(name="verbond_stamnr")
    private String verbond;

    @Column(name="verbond_naam")
    private String verbondName;

    public RawChiroUnit() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStamNumber() {
        return stamNumber;
    }

    public void setStamNumber(String stamNumber) {
        this.stamNumber = stamNumber;
    }

    public String getGewest() {
        return gewest;
    }

    public void setGewest(String gewest) {
        this.gewest = gewest;
    }

    public String getGewestName() {
        return gewestName;
    }

    public void setGewestName(String gewestName) {
        this.gewestName = gewestName;
    }

    public String getVerbond() {
        return verbond;
    }

    public void setVerbond(String verbond) {
        this.verbond = verbond;
    }

    public String getVerbondName() {
        return verbondName;
    }

    public void setVerbondName(String verbondName) {
        this.verbondName = verbondName;
    }
}
