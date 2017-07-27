package com.realdolmen.chiro.domain.payments;


import com.realdolmen.chiro.domain.Address;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TicketType type;

    @Column(name = "Totaal")
    private BigDecimal paymentTotal;

    private String firstNameBuyer;
    private String lastNameBuyer;
    private String emailBuyer;
    private Address addressBuyer;

    public Payment() {
    }

    public Payment(TicketType type, BigDecimal paymentTotal, String firstNameBuyer, String lastNameBuyer, String emailBuyer, Address addressBuyer) {
        this.type = type;
        this.paymentTotal = paymentTotal;
        this.firstNameBuyer = firstNameBuyer;
        this.lastNameBuyer = lastNameBuyer;
        this.emailBuyer = emailBuyer;
        this.addressBuyer = addressBuyer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public BigDecimal getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(BigDecimal paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public String getFirstNameBuyer() {
        return firstNameBuyer;
    }

    public void setFirstNameBuyer(String firstNameBuyer) {
        this.firstNameBuyer = firstNameBuyer;
    }

    public String getLastNameBuyer() {
        return lastNameBuyer;
    }

    public void setLastNameBuyer(String lastNameBuyer) {
        this.lastNameBuyer = lastNameBuyer;
    }

    public String getEmailBuyer() {
        return emailBuyer;
    }

    public void setEmailBuyer(String emailBuyer) {
        this.emailBuyer = emailBuyer;
    }

    public Address getAddressBuyer() {
        return addressBuyer;
    }

    public void setAddressBuyer(Address addressBuyer) {
        this.addressBuyer = addressBuyer;
    }
}
