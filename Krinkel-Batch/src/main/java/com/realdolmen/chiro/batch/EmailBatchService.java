package com.realdolmen.chiro.batch;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.service.EmailSenderService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailBatchService {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(EmailBatchService.class);

	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private RegistrationParticipantRepository registrationParticipantRepository;

	@Scheduled(cron = "${mail.cron.timer}")
	public void sendEmails() {
		logger.info("email batch service running...");
		List<RegistrationCommunication> regComs = registrationCommunicationRepository.findAllWaitingAndFailed();
		regComs.forEach(r -> {
			RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(r.getAdNumber());
			emailSenderService.sendMail(participant);
		});
		Integer i;
	}
}
