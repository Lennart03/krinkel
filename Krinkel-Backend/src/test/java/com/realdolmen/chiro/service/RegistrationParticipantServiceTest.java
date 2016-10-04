package com.realdolmen.chiro.service;

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
    }

    @Test
    public void saveShouldReturnParticipantAfter() {
        Mockito.when(repo.save(participant)).thenReturn(participant);
        Assert.assertSame(participant, registrationParticipantService.save(participant));
        Mockito.verify(repo).save(participant);
    }
}