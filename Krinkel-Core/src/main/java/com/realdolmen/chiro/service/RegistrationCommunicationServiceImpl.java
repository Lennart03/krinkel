package com.realdolmen.chiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.SendStatus;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;

@Service
public class RegistrationCommunicationServiceImpl implements RegistrationCommunicationService {

	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

	@Override
	public void addNewToRegistrationCommunication(String adNumber) {
		RegistrationCommunication registrationCommunication = new RegistrationCommunication();
		registrationCommunication.setAdNumber(adNumber);
		registrationCommunication.setCommunicationAttempt(0);
		registrationCommunication.setStatus(SendStatus.WAITING);
		registrationCommunicationRepository.save(registrationCommunication);
	}
}
