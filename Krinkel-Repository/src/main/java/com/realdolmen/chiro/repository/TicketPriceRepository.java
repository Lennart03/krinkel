package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.payments.TicketPrice;
import com.realdolmen.chiro.domain.payments.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketPriceRepository extends JpaRepository<TicketPrice, Integer> {

    List<TicketPrice> findByTicketType(TicketType ticketType);

    TicketPrice findByTicketTypeAndTicketAmount(TicketType ticketType, Integer ticketAmount);
}
