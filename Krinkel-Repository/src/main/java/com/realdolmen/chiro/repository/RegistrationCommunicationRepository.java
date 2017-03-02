package com.realdolmen.chiro.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realdolmen.chiro.domain.RegistrationCommunication;

@Repository
public interface RegistrationCommunicationRepository extends JpaRepository<RegistrationCommunication, Long> {
    RegistrationCommunication findByAdNumber(String adNumber);

    @Query(value = "SELECT r FROM RegistrationCommunication r WHERE r.status IN (com.realdolmen.chiro.domain.SendStatus.WAITING, com.realdolmen.chiro.domain.SendStatus.FAILED)")
    List<RegistrationCommunication> findAllWaitingAndFailed();

    @Query(value = "SELECT r FROM RegistrationCommunication r WHERE r.status IN (com.realdolmen.chiro.domain.SendStatus.SENDUPDATE)")
    List<RegistrationCommunication> findAllSendUpdate();
}
