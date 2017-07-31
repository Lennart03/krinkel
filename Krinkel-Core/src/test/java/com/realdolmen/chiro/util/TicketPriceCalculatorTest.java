package com.realdolmen.chiro.util;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TicketPriceCalculatorTest {

    private TicketPriceCalculator ticketPriceCalculator = new TicketPriceCalculator();

    @Test
    public void calculateTotalTicketPrice() throws Exception {
        BigDecimal totalPrice = ticketPriceCalculator.calculateTotalTicketPrice(new BigDecimal(10), 1, new BigDecimal(0));
        Assert.assertEquals(new BigDecimal(10), totalPrice);
    }

    @Test
    public void calculateTotalTicketPriceZeroTimesOrdered() throws Exception {
        BigDecimal totalPrice = ticketPriceCalculator.calculateTotalTicketPrice(new BigDecimal(10), 0, new BigDecimal(0.5));
        Assert.assertEquals(new BigDecimal(0), totalPrice);
    }

    @Test
    public void calculateTotalPricePriceTicketIsZero() throws Exception {
        BigDecimal totalPrice = ticketPriceCalculator.calculateTotalTicketPrice(new BigDecimal(0), 1, new BigDecimal(0.5));
        Assert.assertEquals(new BigDecimal(0.5), totalPrice);
    }

    @Test
    public void calculateTotalPriceNumberOfTimesOrdered() throws Exception {
        BigDecimal totalPrice = ticketPriceCalculator.calculateTotalTicketPrice(new BigDecimal(10.5), 6, new BigDecimal(0.5));
        Assert.assertEquals(new BigDecimal(63.5), totalPrice);
    }

}