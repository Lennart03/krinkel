package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.payments.Payment;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.mspservice.MultiSafePayTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * This service is used to order tickets.
 */
@Service
public class TicketService {

    private MultiSafePayTicketService multiSafePayTicketService;

    private final BigDecimal priceTrainTicket = new BigDecimal(11.50);
    private final Map<Integer, BigDecimal> priceCoupons;

    private enum Coupon {

        EIGHT(8),
        SIXTEEN(16),
        TWENTY_FIVE(25),
        THIRTY_FIVE(35),
        FOURTY_FIVE(45),
        NINETEEN(90);

        private final int number;

        Coupon(final int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }


    @Autowired
    public TicketService(MultiSafePayTicketService multiSafePayTicketService) {
        this.multiSafePayTicketService = multiSafePayTicketService;
        this.priceCoupons = new HashMap<>();
        priceCoupons.put(Coupon.EIGHT.number, new BigDecimal(5));
        priceCoupons.put(Coupon.SIXTEEN.number, new BigDecimal(10));
        priceCoupons.put(Coupon.TWENTY_FIVE.number, new BigDecimal(15));
        priceCoupons.put(Coupon.THIRTY_FIVE.number, new BigDecimal(20));
        priceCoupons.put(Coupon.FOURTY_FIVE.number, new BigDecimal(25));
        priceCoupons.put(Coupon.NINETEEN.number, new BigDecimal(50));
    }

    public Payment createPayment(TicketType type, int ticketAmount) {
        return null;
    }

    public BigDecimal getPriceTrainTicket() {
        return priceTrainTicket;
    }

    public Map<Integer, BigDecimal> getPriceCoupons() {
        return priceCoupons;
    }
}
