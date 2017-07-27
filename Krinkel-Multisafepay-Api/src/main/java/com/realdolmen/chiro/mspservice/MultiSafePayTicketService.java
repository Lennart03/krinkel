package com.realdolmen.chiro.mspservice;

import com.realdolmen.chiro.domain.payments.Payment;
import com.realdolmen.chiro.mspdto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to call MultiSafePay api for the orders of tickets
 */
@Service
public class MultiSafePayTicketService {

    private MultiSafePayConfiguration configuration;

    @Autowired
    public MultiSafePayTicketService(MultiSafePayConfiguration configuration) {
        this.configuration = configuration;
    }


    public OrderDto createPayment(Payment payment) {
        return null;
    }
}
