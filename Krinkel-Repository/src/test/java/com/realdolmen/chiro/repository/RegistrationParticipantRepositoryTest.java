package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.CampGround;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.VolunteerFunction;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegistrationParticipantRepositoryTest extends SpringIntegrationTest{
	private static final String AD_NUMBER = "876543";
	
	@Autowired
	private RegistrationParticipantRepository registrationParticipantRepository;

	@Test
	public void shouldContainVolunteerFields() {
		assertNotNull(registrationParticipantRepository);
		assertNotNull(registrationParticipantRepository.findOne(60L));
		RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(AD_NUMBER);
		assertNotNull(participant);
		assertEquals("Jos", participant.getFirstName());
		RegistrationVolunteer volunteer = (RegistrationVolunteer)participant;
		CampGround campGround = volunteer.getCampGround();
		String s = campGround.toString();
		assertEquals("KEMPEN", s);
		assertEquals(VolunteerFunction.Preset.CAMPGROUND, volunteer.getFunction().getPreset());
	}
	
	@Test
	public void shouldReturnAParticipant(){
		RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(AD_NUMBER);
		System.out.println(AD_NUMBER);
		assertNotNull(participant);
	}

	@Test
	public void shouldReturnAListWithParticipantsWithStatusPaid(){
		List<RegistrationParticipant> participants =
				registrationParticipantRepository.findRegistrationParticipantsWithStatusPAID();
		assertNotNull(participants);
		assertEquals(1, participants.size());
		System.out.println(participants.size());
	}
}