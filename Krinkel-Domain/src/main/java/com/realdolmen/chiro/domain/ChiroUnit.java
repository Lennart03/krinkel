package com.realdolmen.chiro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO: See http://stackoverflow.com/questions/2355728/jpql-create-new-object-in-select-statement-avoid-or-embrace
 *  Creating new entity in JPQL select statement -> Distill verbond and gewest entities from the ChiroUnit tables.
 */
@Entity
@Table(name="groepen")
public class ChiroUnit {

    @Column(name="groep_stamnr")
    @Id
    private String stamNumber;

    @Column(name="groep_naam")
    private String name;

    @Column(name="gewest_stamnr")
    private String gewest;

    @Column(name="verbond_stamnr")
    private String verbond;

    public ChiroUnit() {
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

    public String getVerbond() {
        return verbond;
    }

    public void setVerbond(String verbond) {
        this.verbond = verbond;
    }
}
