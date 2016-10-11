package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationParticipantService {
    public final static Integer PRICE_IN_EUROCENTS = 11000;

    @Autowired
    private RegistrationParticipantRepository repository;

    public RegistrationParticipant save(RegistrationParticipant registration) {
        if(repository.findByAdNumber(registration.getAdNumber()) == null) {
            return repository.save(registration);
        }
        return null;
    }
}
