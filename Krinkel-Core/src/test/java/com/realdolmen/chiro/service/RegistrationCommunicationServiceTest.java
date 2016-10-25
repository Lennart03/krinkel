package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.TestConfig;
import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes={TestConfig.class})
public class RegistrationCommunicationServiceTest extends SpringIntegrationTest{
	
	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

	@Test
	public void shouldFindAllWaitingAndFailed(){
		List<RegistrationCommunication>communications = registrationCommunicationRepository.findAllWaitingAndFailed();
		System.out.println(communications.size());
		assertEquals(6, communications.size());
	}

}
