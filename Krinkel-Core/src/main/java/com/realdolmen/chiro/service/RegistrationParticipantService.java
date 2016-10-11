package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationParticipantService {

    @Autowired
    private RegistrationParticipantRepository repository;

    public RegistrationParticipant save(RegistrationParticipant registration) {
        if(repository.findByAdNumber(registration.getAdNumber()) == null) {
            return repository.save(registration);
        }
        return null;
    }

    public List<RegistrationParticipant> findParticipantsByUnit(String stamNumber){
        return repository.findParticipantsByUnit(stamNumber);
    }
}
