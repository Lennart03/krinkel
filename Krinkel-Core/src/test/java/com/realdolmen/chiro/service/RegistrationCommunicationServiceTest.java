package com.realdolmen.chiro.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.SendStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegistrationCommunicationServiceTest {
	
	@Autowired
	private RegistrationCommunicationService registrationCommunicationService;

	@Test
	public void shouldFindAllWaitingAndFailed(){
		List<SendStatus>statuses = Arrays.asList(SendStatus.FAILED,SendStatus.WAITING);
		List<RegistrationCommunication>communications = registrationCommunicationService.findAllWaitingAndFailedRegistrationCommunications();
		assertEquals(6, communications.size());
	}
	

}
