package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.CampGround;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.VolunteerFunction;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
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
		assertEquals(CampGround.KEMPEN, volunteer.getCampGround());
		assertEquals(VolunteerFunction.Preset.CAMPGROUND, volunteer.getFunction().getPreset());
	}
	
	@Test
	public void shouldReturnAParticipant(){
		RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(AD_NUMBER);
		assertNotNull(participant);
	}

	@Test
	public void shouldReturnAListWithParticipantsWithStatusPaid(){
		List<RegistrationParticipant> participants =
				registrationParticipantRepository.findRegistrationParticipantsWithStatusPAID();
		assertNotNull(participants);
		assertEquals(1, participants.size());
	}

    @Test
    public void shouldUpdateLastChange(){
        RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(AD_NUMBER);
        Date timeStamp = participant.updateLastChange();
        registrationParticipantRepository.save(participant);
        participant = registrationParticipantRepository.findByAdNumber(AD_NUMBER);
        Assert.assertEquals(timeStamp, participant.getLastChange());
    }
}
