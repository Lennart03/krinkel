package com.realdolmen.chiro.mspdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This object represents the data part in the OrderDto and StatusDto objects.
 * It contains the actual interesting bits. This class is a combination of the
 * fields that are used in both json objects.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private String order_id;
    private String payment_url;

    private String transaction_id;
    private String status;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Data{" +
                "order_id='" + order_id + '\'' +
                ", payment_url='" + payment_url + '\'' +
                '}';
    }
}
