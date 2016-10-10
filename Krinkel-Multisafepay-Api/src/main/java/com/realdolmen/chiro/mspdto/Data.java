package com.realdolmen.chiro.mspdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private String order_id;
    private String order_url;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_url() {
        return order_url;
    }

    public void setOrder_url(String order_url) {
        this.order_url = order_url;
    }
}
