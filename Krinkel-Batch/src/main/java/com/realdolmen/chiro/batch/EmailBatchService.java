package com.realdolmen.chiro.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;

@Service
public class EmailBatchService {

	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;
	
	@Scheduled
	public void sendEmails(){
		
	}

}
