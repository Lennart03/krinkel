package com.realdolmen.chiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.SendStatus;

public interface RegistrationCommunicationService {
	void addNewToRegistrationCommunication(String adNumber);
	List<RegistrationCommunication> findAllWaitingAndFailedRegistrationCommunications();

}
