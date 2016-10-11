package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationVolunteerService {

	@Autowired
	private RegistrationVolunteerRepository repository;
	
	@Autowired
	private EmailSenderService emailSenderService;

	public RegistrationVolunteer save(RegistrationVolunteer registration) {
		if (repository.findByAdNumber(registration.getAdNumber()) == null) {
			emailSenderService.sendMail(registration);
			return repository.save(registration);
		}
		return null;
	}
}
