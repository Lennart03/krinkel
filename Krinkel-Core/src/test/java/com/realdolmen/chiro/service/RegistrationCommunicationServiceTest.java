package com.realdolmen.chiro.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.realdolmen.chiro.config.TestConfig;
import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
@ContextConfiguration(classes=TestConfig.class)
public class RegistrationCommunicationServiceTest {
	
	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

	@Test
	public void shouldFindAllWaitingAndFailed(){
		List<RegistrationCommunication>communications = registrationCommunicationRepository.findAllWaitingAndFailed();
		assertEquals(6, communications.size());
	}
	

}
