package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.TestConfig;
import com.realdolmen.chiro.domain.payments.TicketPrice;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

@ContextConfiguration(classes={TestConfig.class})
public class TicketServiceTest extends SpringIntegrationTest {


    @Autowired
    private TicketService ticketService;

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

}