package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationVolunteerRepository extends JpaRepository<RegistrationVolunteer, Long> {
    RegistrationVolunteer findByAdNumber(String adNumber);
}
