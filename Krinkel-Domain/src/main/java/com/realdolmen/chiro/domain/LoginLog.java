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

    public LoginLog() {
    }

    public LoginLog(String adNumber) {
        this.adNumber = adNumber;
        this.stamp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdNummer() {
        return adNumber;
    }

    public void setAdNummer(String adNummer) {
        this.adNumber = adNummer;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}
