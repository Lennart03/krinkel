package com.realdolmen.chiro.service;

import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationCommunicationService {
	void addNewToRegistrationCommunication(String adNumber);

}
