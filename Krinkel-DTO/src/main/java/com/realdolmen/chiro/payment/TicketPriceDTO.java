package com.realdolmen.chiro.payment;

/**
 * POJO to create JSON for ticket prices
 */
public class TicketPriceDTO {

    private int ticketamount;
    private double price;
    private double transportationCost;

    public TicketPriceDTO(int ticketamount, double price, double transportationCost) {
        this.ticketamount = ticketamount;
        this.price = price;
        this.transportationCost = transportationCost;
    }

    public int getTicketamount() {
        return ticketamount;
    }

    public void setTicketamount(int ticketamount) {
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

    @Override
    public String toString() {
        return "TicketPriceDTO{" +
                "ticketamount=" + ticketamount +
                ", price=" + price +
                ", transportationCost=" + transportationCost +
                '}';
    }
}
