package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.SendStatus;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationVolunteerService {

	@Autowired
	private RegistrationVolunteerRepository repository;
	
	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

	public RegistrationVolunteer save(RegistrationVolunteer registration) {
		if (repository.findByAdNumber(registration.getAdNumber()) == null) {
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
