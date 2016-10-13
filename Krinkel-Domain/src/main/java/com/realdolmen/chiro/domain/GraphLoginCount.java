package com.realdolmen.chiro.domain;

import java.util.Date;

/**
 * Created by WVDAZ49 on 13/10/2016.
 */
public class GraphLoginCount {

    private Date date;
    private Long count;

    public GraphLoginCount() {
    }

    public GraphLoginCount(Date date, Long count) {
        this.date = date;
        this.count = count;
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
}
