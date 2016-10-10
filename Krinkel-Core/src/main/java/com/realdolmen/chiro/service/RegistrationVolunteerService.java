package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationVolunteerService {

	@Autowired
	private RegistrationVolunteerRepository repository;
	
	@Autowired
	private RegistrationCommunicationService registrationCommunicationService;

	public RegistrationVolunteer save(RegistrationVolunteer registration) {
		if (repository.findByAdNumber(registration.getAdNumber()) == null) {
			registrationCommunicationService.addNewToRegistrationCommunication(registration.getAdNumber());
			return repository.save(registration);
		}
		return null;
	}
}
