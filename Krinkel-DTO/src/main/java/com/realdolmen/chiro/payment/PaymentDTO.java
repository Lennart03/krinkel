package com.realdolmen.chiro.payment;

public class PaymentDTO {

    private String paymentURL;
    private Integer paymentId;

    public PaymentDTO() {
    }

    public PaymentDTO(String paymentURL, Integer paymentId) {
        this.paymentURL = paymentURL;
        this.paymentId = paymentId;
    }

    public String getPaymentURL() {
        return paymentURL;
    }

    public void setPaymentURL(String paymentURL) {
        this.paymentURL = paymentURL;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
}
