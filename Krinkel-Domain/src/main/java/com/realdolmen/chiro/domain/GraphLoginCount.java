package com.realdolmen.chiro.domain;

import java.util.Date;

/**
 * Created by WVDAZ49 on 13/10/2016.
 */
public class GraphLoginCount {

    private Date date;
    private Long count;
    private String stamNumber;

    public GraphLoginCount() {
    }

    public GraphLoginCount(Date date, Long count, String stamNumber) {
        this.date = date;
        this.count = count;
        this.stamNumber = stamNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getStamNumber() {
        return stamNumber;
    }

    public void setStamNumber(String stamNumber) {
        this.stamNumber = stamNumber;
    }
}
