package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SendStatus;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationParticipantService {

    @Autowired
    private RegistrationParticipantRepository repository;
    
	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

    public RegistrationParticipant save(RegistrationParticipant registration) {
        if(repository.findByAdNumber(registration.getAdNumber()) == null) {
        	RegistrationCommunication regCom = new RegistrationCommunication();
			regCom.setAdNumber(registration.getAdNumber());
			regCom.setCommunicationAttempt(0);
			regCom.setStatus(SendStatus.WAITING);
        	registrationCommunicationRepository.save(regCom);
        	return repository.save(registration);
        }
        return null;
    }
}
