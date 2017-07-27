package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.payments.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
