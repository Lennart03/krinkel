package com.realdolmen.chiro.batch;

import java.util.List;

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

	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private RegistrationParticipantRepository registrationParticipantRepository;
	
//	@Autowired
//	private RegistrationVolunteerRepository registrationVolunteerRepository;
	
	@Scheduled(cron = "*/2 * * * * ")
	public void sendEmails(){
		List<RegistrationCommunication>regComs = registrationCommunicationRepository.findAllWaitingAndFailed();
		regComs.forEach(r->{
			RegistrationParticipant participant= registrationParticipantRepository.findByAdNumber(r.getAdNumber());
			if(participant instanceof RegistrationVolunteer){
				emailSenderService.sendMail((RegistrationVolunteer)participant);
			}else{
				emailSenderService.sendMail(participant);
			}
		});
	}

}
