package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationParticipantService {

    @Autowired
    private RegistrationParticipantRepository repository;

    public RegistrationParticipant save(RegistrationParticipant registration) {
        if(repository.findAllByAdNumber(registration.getAdNumber()).size() == 0) {
            return repository.save(registration);
        }
        return null;
    }
}
