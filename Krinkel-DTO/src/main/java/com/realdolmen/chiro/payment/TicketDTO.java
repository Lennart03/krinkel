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
     * This is the number of tickets requested
     */
    private Integer ticketAmount;
    /**
     * First name of the person ordering the ticket
     */
    private String firstName;
    /**
     * Last name of the person ordering the ticket
     */
    private String lastName;
    /**
     * Email of the person buying the ticket
     */
    private String email;
    /**
     * Phone number of the person buying the ticket
     */
    private String phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "type=" + type +
                ", ticketAmount=" + ticketAmount +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
