package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.ConfirmationLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationLinkRepository extends JpaRepository<ConfirmationLink, Long> {
    ConfirmationLink findByAdNumber(String adNumber);
}
