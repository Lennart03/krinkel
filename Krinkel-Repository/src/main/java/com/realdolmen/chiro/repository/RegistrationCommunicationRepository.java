package com.realdolmen.chiro.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.SendStatus;

@Repository
public interface RegistrationCommunicationRepository extends JpaRepository<RegistrationCommunication, Long> {
    RegistrationCommunication findByAdNumber(String adNumber);
    @Query(value="SELECT * FROM registration_communication WHERE status IN ('WAITING', 'FAILED')", nativeQuery=true)
    List<RegistrationCommunication> findAllWaitingAndFailed();
}
