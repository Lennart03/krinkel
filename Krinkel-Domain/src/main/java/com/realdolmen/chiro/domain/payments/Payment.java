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

    @Column(name = "Aantal_tickets")
    private Integer numberOfTickets;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status= PaymentStatus.INITIATED;

    private String firstNameBuyer;
    private String lastNameBuyer;
    private String emailBuyer;
    private String phoneNumberBuyer;
    private Address addressBuyer;

    public Payment() {
    }

    public Payment(TicketType type, BigDecimal paymentTotal, Integer numberOfTickets, String firstNameBuyer, String lastNameBuyer, String emailBuyer, String phoneNumberBuyer, Address addressBuyer) {
        this.type = type;
        this.paymentTotal = paymentTotal;
        this.numberOfTickets = numberOfTickets;
        this.firstNameBuyer = firstNameBuyer;
        this.lastNameBuyer = lastNameBuyer;
        this.emailBuyer = emailBuyer;
        this.phoneNumberBuyer = phoneNumberBuyer;
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

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
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

    public String getPhoneNumberBuyer() {
        return phoneNumberBuyer;
    }

    public void setPhoneNumberBuyer(String phoneNumberBuyer) {
        this.phoneNumberBuyer = phoneNumberBuyer;
    }

    public Address getAddressBuyer() {
        return addressBuyer;
    }

    public void setAddressBuyer(Address addressBuyer) {
        this.addressBuyer = addressBuyer;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
