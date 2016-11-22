package com.realdolmen.chiro.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by WVDAZ49 on 13/10/2016.
 */
@Entity
public class LoginLog {
    @Id
    @GeneratedValue
    private Long id;
    private String adNumber;
    @Temporal(TemporalType.DATE)
    private Date stamp;

    private String stamNumber;

    public LoginLog() {
    }

    public LoginLog(String adNumber, String stamNumber) {
        this.adNumber = adNumber;
        this.stamNumber = stamNumber;
        this.stamp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(String adNumber) {
        this.adNumber = adNumber;
    }

    public String getStamNumber() {
        return stamNumber;
    }

    public void setStamNumber(String stamNumber) {
        this.stamNumber = stamNumber;
    }
}
