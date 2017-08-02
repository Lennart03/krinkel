package com.realdolmen.chiro.payment;

/**
 * POJO to create JSON for ticket prices
 */
public class TicketPriceDTO {

    private Integer ticketamount;
    private double price;
    private double transportationCost;

    public Integer getTicketamount() {
        return ticketamount;
    }

    public void setTicketamount(Integer ticketamount) {
        this.ticketamount = ticketamount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(double transportationCost) {
        this.transportationCost = transportationCost;
    }
}
