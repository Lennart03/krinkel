package com.realdolmen.chiro.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A CampHelpMoment is a generalisation of the 'Voorwacht' and 'Nawacht' concepts.
 * Each instance defines a possible date at which volunteers can help with the setup or breakdown of the camp.
 *
 * Only the concrete implementations should be used.
 * No new instances of this entity or its subclasses should be created.
 * Only the existing values already present in the database should be used.
 */
@MappedSuperclass
public abstract class CampHelpMoment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    public CampHelpMoment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString(){
    	SimpleDateFormat dateFrmt = new SimpleDateFormat("dd/MM/yyyy");
    	return dateFrmt.format(getDate());
    }
}
