package com.realdolmen.chiro.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TicketPriceCalculator {

    /**
     * Used to calculate the total price of a ticket order
     * @param priceOneTicket Price of one ticket.
     * @param timesOrdered The amount of tickets wanted.
     * @param transportationCosts Transportation's costs for the tickets
     * @return Total price = (priceOneTicket x timesOrdered) + transportationCosts
     */
    public BigDecimal calculateTotalTicketPrice(BigDecimal priceOneTicket, Integer timesOrdered, BigDecimal transportationCosts) {
        if(timesOrdered.equals(0)) return new BigDecimal(0);
        return priceOneTicket.multiply(new BigDecimal(timesOrdered)).add(transportationCosts);
    }

}
