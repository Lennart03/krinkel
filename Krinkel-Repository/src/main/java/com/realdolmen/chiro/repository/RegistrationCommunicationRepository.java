package com.realdolmen.chiro.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realdolmen.chiro.domain.RegistrationCommunication;

@Repository
public interface RegistrationCommunicationRepository extends JpaRepository<RegistrationCommunication, Long> {
    RegistrationCommunication findByAdNumber(String adNumber);
    @Query("SELECT r FROM RegistrationCommunication r WHERE r.SendStatus in :status")
    List<RegistrationCommunication> findAllWaitingAndFailed(@Param("status")List<String>statuses);
}
