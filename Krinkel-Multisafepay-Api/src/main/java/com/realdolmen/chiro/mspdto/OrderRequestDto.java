package com.realdolmen.chiro.mspdto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderRequestDto {
    private String type = "redirect";
    private String order_id = "abc123";
    private String description = "this is the description";
    private String currency = "EUR";
    private Integer amount = 10000;
    private PaymentOptions payment_options = new PaymentOptions();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public PaymentOptions getPayment_options() {
        return payment_options;
    }

    public void setPayment_options(PaymentOptions payment_options) {
        this.payment_options = payment_options;
    }

    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "type='" + type + '\'' +
                ", order_id='" + order_id + '\'' +
                ", description='" + description + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", payment_options=" + payment_options +
                '}';
    }
}
