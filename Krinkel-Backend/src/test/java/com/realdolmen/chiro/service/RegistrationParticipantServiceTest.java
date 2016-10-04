package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Adress;
import com.realdolmen.chiro.domain.Function;
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
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationParticipantServiceTest {


    @InjectMocks
    private RegistrationParticipantService registrationParticipantService;

    @Mock
    private RegistrationParticipantRepository repo;

    private RegistrationParticipant participant;

    @Before
    public void setUp() {
        participant = new RegistrationParticipant();
        participant.setFirstName("NICK");
        participant.setLastName("HANOT");
        participant.setStamnumber("STAMNUMMER");
        participant.setAdNumber("ADNUMMER");
        participant.setFunction(Function.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());

        Adress adress = new Adress();
        adress.setCity("REET");
        adress.setHouseNumber(69);
        adress.setPostalCode(6969);
        adress.setStreet("REETSESTEENWEG");
        participant.setAdress(adress);
    }


    @Test
    public void saveShouldReturnParticipantAfter() {
        Mockito.when(repo.save(participant)).thenReturn(participant);
        Assert.assertSame(participant, registrationParticipantService.save(participant));
        Mockito.verify(repo).save(participant);
    }



    @Test
    public void saveShouldReturnNULLWhenExisting() {
        Mockito.when(repo.findByAdNumber("ADNUMMER")).thenReturn(null);
        registrationParticipantService.save(participant);
        Assert.assertSame(null, registrationParticipantService.save(participant));
        Mockito.verify(repo, times(2)).save(participant);
    }
}