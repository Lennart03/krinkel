package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationParticipantService {

    @Autowired
    private RegistrationParticipantRepository repository;
    
    @Autowired
    private RegistrationCommunicationService registrationCommunicationService;

    public RegistrationParticipant save(RegistrationParticipant registration) {
        if(repository.findByAdNumber(registration.getAdNumber()) == null) {
        	registrationCommunicationService.addNewToRegistrationCommunication(registration.getAdNumber());
        	return repository.save(registration);
        }
        return null;
    }
}
