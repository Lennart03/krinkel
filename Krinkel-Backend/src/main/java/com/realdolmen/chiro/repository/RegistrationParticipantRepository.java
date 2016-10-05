package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationParticipantRepository extends JpaRepository<RegistrationParticipant, Long> {
    RegistrationParticipant findByAdNumber(String adNumber);
}
