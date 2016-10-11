package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationParticipantServiceTest {


    @InjectMocks
    private RegistrationParticipantService registrationParticipantService;

    @Mock
    private RegistrationParticipantRepository repo;

    @Mock
    private EmailSenderService emailSenderService;
    
    private RegistrationParticipant participant;

    @Before
    public void setUp() {
        participant = new RegistrationParticipant();
        participant.setFirstName("NICK");
        participant.setLastName("HANOT");
        participant.setStamnumber("STAMNUMMER");
        participant.setAdNumber("ADNUMMER");
        participant.setRole(Role.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());

        Address address = new Address();
        address.setCity("REET");
        address.setHouseNumber("69");
        address.setPostalCode(6969);
        address.setStreet("REETSESTEENWEG");
        participant.setAddress(address);
    }


    @Test
    public void saveShouldReturnParticipantAfter() {
        Mockito.when(repo.save(participant)).thenReturn(participant);
        Assert.assertSame(participant,
        		registrationParticipantService.save(participant));
        Mockito.verify(repo).save(participant);
    }



    @Test
    public void saveShouldReturnNULLWhenExisting() {
        Mockito.when(repo.findByAdNumber("ADNUMMER")).thenReturn(null);
        registrationParticipantService.save(participant);
        Assert.assertSame(null, registrationParticipantService.save(participant));
        Mockito.verify(repo, Mockito.times(2)).save(participant);
    }
}