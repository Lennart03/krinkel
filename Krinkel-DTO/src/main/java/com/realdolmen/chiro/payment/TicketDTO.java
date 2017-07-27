package com.realdolmen.chiro.payment;

import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.payments.TicketType;

/**
 * DTO to transfer data of the ticket order from frontend to the TicketService
 */
public class TicketDTO {

    /**
     * The requested type of ticket
     */
    private TicketType type;
    /**
     * This is the number of tickets requests when food/drink tickets are ordered
     */
    private Integer ticketAmount;
    /**
     * The number of times the ticket is requested
     */
    private Integer timesOrdered;
    /**
     * First name of the person ordering the ticket
     */
    private String firstName;
    /**
     * Last name of the person ordering the ticket
     */
    private String lastName;
    /**
     * Address given by the person in the frontend
     */
    private Address address;

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Integer getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(Integer ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public Integer getTimesOrdered() {
        return timesOrdered;
    }

    public void setTimesOrdered(Integer timesOrdered) {
        this.timesOrdered = timesOrdered;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
