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
	private EmailSenderService emailSenderService;

    public RegistrationParticipant save(RegistrationParticipant registration) {
        if(repository.findByAdNumber(registration.getAdNumber()) == null) {
			emailSenderService.sendMail(registration);
        	return repository.save(registration);
        }
        return null;
    }
}
