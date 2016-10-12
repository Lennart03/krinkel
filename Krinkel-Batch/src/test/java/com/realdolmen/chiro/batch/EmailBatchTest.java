package com.realdolmen.chiro.batch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realdolmen.chiro.config.TestConfig;
import com.realdolmen.chiro.configuration.ApplicationConfiguration;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class,ApplicationConfiguration.class})
public class EmailBatchTest {

	@Autowired
	private RegistrationParticipantRepository registrationParticipantRepository;

	@Test
	public void shouldContainVolunteerFields() {
		RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber("876543");
		RegistrationVolunteer volunteer = (RegistrationVolunteer)participant;
		assertEquals("CAMPGROUND", volunteer.getCampGround().toString());
	}

}
