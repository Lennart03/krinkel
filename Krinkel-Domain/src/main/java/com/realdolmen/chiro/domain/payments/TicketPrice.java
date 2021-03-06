package com.realdolmen.chiro.domain.payments;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class TicketPrice {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer ticketAmount;

    private BigDecimal price;

    private BigDecimal transportationcosts;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    public TicketPrice() {
    }

    public TicketPrice(Integer ticketAmount, BigDecimal price, BigDecimal transportationcosts, TicketType ticketType) {
        this.ticketAmount = ticketAmount;
        this.price = price;
        this.transportationcosts = transportationcosts;
        this.ticketType = ticketType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(Integer ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTransportationcosts() {
        return transportationcosts;
    }

    public void setTransportationcosts(BigDecimal transportationcosts) {
        this.transportationcosts = transportationcosts;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public String toString() {
        return "TicketPrice{" +
                "id=" + id +
                ", ticketAmount=" + ticketAmount +
                ", price=" + price +
                ", transportationcosts=" + transportationcosts +
                ", ticketType=" + ticketType +
                '}';
    }
}
