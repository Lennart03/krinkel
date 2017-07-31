package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.TestConfig;
import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.payments.TicketPrice;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.payment.TicketDTO;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

@ContextConfiguration(classes = {TestConfig.class})
public class TicketServiceTest extends SpringIntegrationTest {

    private final String street = "Elmstraat";
    private final String housenumber = "666";
    private final String city = "ciry";
    private final String firstName = "Jax";
    private final String lastName = "Teller";
    private final String email = "jax.teller@soa.com";
    private final String phone = "0458976453";

    private final Integer postalCode = 1111;
    private final Integer ticketAmount = 1;
    private final Integer timesOrdered = 1;

    private final Address address = new Address(street, housenumber, postalCode, city);

    private TicketDTO dto;

    @Autowired
    private TicketService ticketService;

    public void setup() {
        dto = new TicketDTO();
        dto.setType(TicketType.TREIN);
        dto.setTicketAmount(ticketAmount);
        dto.setTimesOrdered(timesOrdered);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setPhoneNumber(phone);
        dto.setAddress(address);
    }

    @Test
    public void getPricesForTickets() throws Exception {
        List<TicketPrice> prices = ticketService.getPricesForTickets();
        Assert.assertEquals(7, prices.size());
        TicketPrice price = prices.get(0);
        Assert.assertEquals(TicketType.TREIN, price.getTicketType());
        Assert.assertEquals(new BigDecimal("11.50"), price.getPrice());
        Assert.assertEquals(new BigDecimal(0.50), price.getTransportationcosts());
        price = prices.get(3);
        Assert.assertEquals(TicketType.BON, price.getTicketType());
    }

    @Test
    public void getPricesForTicketsWithType() throws Exception {
        List<TicketPrice> pricesForTrain = ticketService.getPricesForTickets(TicketType.TREIN);
        Assert.assertEquals(1, pricesForTrain.size());
        TicketPrice price = pricesForTrain.get(0);
        Assert.assertEquals(TicketType.TREIN, price.getTicketType());
        Assert.assertEquals(new BigDecimal("11.50"), price.getPrice());
        Assert.assertEquals(new BigDecimal(0.50), price.getTransportationcosts());
        List<TicketPrice> pricesForCoupons = ticketService.getPricesForTickets(TicketType.BON);
        Assert.assertEquals(6, pricesForCoupons.size());
        price = pricesForCoupons.get(0);
        Assert.assertEquals(TicketType.BON, price.getTicketType());
    }

    @Test
    public void createPayment() throws Exception {
        ticketService.createPayment(dto);

    }
}