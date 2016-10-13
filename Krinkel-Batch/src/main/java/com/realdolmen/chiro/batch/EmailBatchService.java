package com.realdolmen.chiro.batch;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.service.EmailSenderService;

@Service
public class EmailBatchService {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(EmailBatchService.class);

	private List<RegistrationCommunication> regComs = new ArrayList<>();

	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private RegistrationParticipantRepository registrationParticipantRepository;

	// @Autowired
	// private RegistrationVolunteerRepository registrationVolunteerRepository;

	@Scheduled(cron = "*/10 * * * * *")
	public void sendEmails() {
		logger.info("email batch service running...");
		regComs = registrationCommunicationRepository.findAllWaitingAndFailed();
		regComs.forEach(r -> {
			RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(r.getAdNumber());
			if (participant instanceof RegistrationVolunteer) {
				emailSenderService.sendMail((RegistrationVolunteer) participant);
			} else {
				emailSenderService.sendMail(participant);
			}
		});
	}

	

}
