package com.realdolmen.chiro.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realdolmen.chiro.domain.RegistrationCommunication;

@Repository
public interface RegistrationCommunicationRepository extends JpaRepository<RegistrationCommunication, Long> {
    RegistrationCommunication findByAdNumber(String adNumber);
}
