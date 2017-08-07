package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.payments.Payment;
import com.realdolmen.chiro.domain.payments.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findAllByType(TicketType type);

}
