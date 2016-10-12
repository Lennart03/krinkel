package com.realdolmen.chiro.domain;

public class Colleague {
    private String adnr;
    private String first_name;
    private String last_name;
    private String afdeling;

    public Colleague(String adnr, String first_name, String last_name, String afdeling) {
        this.adnr = adnr;
        this.first_name = first_name;
        this.last_name = last_name;
        this.afdeling = afdeling;
    }

    public String getAdnr() {
        return adnr;
    }

    public void setAdnr(String adnr) {
        this.adnr = adnr;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(String afdeling) {
        this.afdeling = afdeling;
    }
}
