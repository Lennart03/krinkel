package com.realdolmen.chiro.repository;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.realdolmen.chiro.config.TestApplication;
import com.realdolmen.chiro.domain.RegistrationParticipant;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {TestApplication.class})
@ContextConfiguration(classes={TestApplication.class}) // Spring Boot config (includes component scan)
@Transactional // Rollback after each test.
@TestPropertySource(locations="classpath:application-test.properties") // Different set of properties to set H2 as DB.
public class RegistrationParticipantRepositoryTest {
	private static final String AD_NUMBER = "876543";
	
	@Autowired
	private RegistrationParticipantRepository registrationParticipantRepository;

//	@Test
//	public void shouldContainVolunteerFields() {
//		System.err.println("testtestsetsets");
//		assertNotNull(registrationParticipantRepository);
//		
//		RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(AD_NUMBER);
//		assertNotNull(participant);
//		assertEquals("Jos", participant.getFirstName());
//		RegistrationVolunteer volunteer = (RegistrationVolunteer)participant;
//		assertEquals("CAMPGROUND", volunteer.getCampGround().toString());
//	}
	
	@Test
	public void shouldReturnAParticipant(){
		RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(AD_NUMBER);
		System.out.println(AD_NUMBER);
		assertNotNull(participant);
	}
	
	
	@Test
	public void shouldReturnAListWithParticipantsWithStatusPaid(){
		List<RegistrationParticipant>participants = registrationParticipantRepository.findRegistrationParticipantsWithStatusPAID();
		assertNotNull(participants);
		System.out.println(participants.size());
	}
}
