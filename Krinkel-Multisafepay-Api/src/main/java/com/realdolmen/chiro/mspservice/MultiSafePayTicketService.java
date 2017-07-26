package com.realdolmen.chiro.mspservice;

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


}
