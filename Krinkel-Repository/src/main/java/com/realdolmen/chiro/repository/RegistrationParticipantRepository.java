package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationParticipantRepository extends JpaRepository<RegistrationParticipant, Long> {
    RegistrationParticipant findByAdNumber(String adNumber);

    @Query("SELECT p FROM RegistrationParticipant p WHERE " +
            "(SUBSTRING(p.stamnumber, 1, 2) = SUBSTRING(?1, 1, 2)" +
            "OR SUBSTRING(p.stamnumber, 1, 3) = SUBSTRING(?1, 1, 3))" +
            " AND " +
            "(SUBSTRING(p.stamnumber, LOCATE('/', p.stamnumber)+1, 4) = SUBSTRING(?1, 3, 4)" +
            "OR SUBSTRING(p.stamnumber, LOCATE('/', p.stamnumber)+1, 4) = SUBSTRING(?1, 4, 4)" +
            ")"
    )
    List<RegistrationParticipant> findParticipantsByGroup(String s);
}