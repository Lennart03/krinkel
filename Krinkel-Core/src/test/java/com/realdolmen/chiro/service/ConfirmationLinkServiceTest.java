package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.ConfirmationLinkRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ConfirmationLinkServiceTest {

    @InjectMocks
    private ConfirmationLinkService service;

    @Mock
    private ConfirmationLinkRepository confirmationLinkRepository;

    @Mock
    private RegistrationParticipantRepository registrationRepository;

    private RegistrationParticipant participant;

    private ConfirmationLink confirmationLink;


    @Before
    public void setUp(){
        confirmationLink = new ConfirmationLink("123456", "3179c3:ec6e28a7ef:-8000");

        this.participant = new RegistrationParticipant(
                "123456",
                "hermione@hogwarts.example",
                "Hermione",
                "Granger",
                new Date(),
                "AG0104",
                Gender.WOMAN,
                Role.ASPI,
                Eatinghabbit.VEGI
        );
        this.participant.setStatus(Status.PAID);
    }

    @Test
    public void checkTokenWithCorrectTokenUpdatesRegistrationStatus(){
        Mockito.when(confirmationLinkRepository.findByAdNumber("123456")).thenReturn(confirmationLink);
        Mockito.when(registrationRepository.findByAdNumber("123456")).thenReturn(participant);
        boolean result = service.checkToken("123456", "3179c3:ec6e28a7ef:-8000");

        assertTrue(result);
        assertEquals(participant.getStatus(), Status.CONFIRMED);

        Mockito.verify(confirmationLinkRepository).findByAdNumber("123456");
        Mockito.verify(registrationRepository).findByAdNumber("123456");
        Mockito.verify(registrationRepository).save(participant);

        Mockito.verifyNoMoreInteractions(confirmationLinkRepository);
        Mockito.verifyNoMoreInteractions(registrationRepository);
    }

    @Test
    public void checkTokenWithWrongTokenDoesNotUpdateRegistrationStatus(){
        Mockito.when(confirmationLinkRepository.findByAdNumber("123456")).thenReturn(confirmationLink);
        Mockito.when(registrationRepository.findByAdNumber("123456")).thenReturn(participant);
        boolean result = service.checkToken("123456", "3179c3:aaae28a7ef:-8000");

        assertFalse(result);
        assertEquals(participant.getStatus(), Status.PAID);

        Mockito.verify(confirmationLinkRepository).findByAdNumber("123456");

        Mockito.verifyNoMoreInteractions(confirmationLinkRepository);
        Mockito.verifyNoMoreInteractions(registrationRepository);
    }

    @Test
    public void checkTokenWithCorrectTokenAndStatusToBePaidDoesNotUpdateRegistrationStatus(){
        Mockito.when(confirmationLinkRepository.findByAdNumber("123456")).thenReturn(confirmationLink);
        Mockito.when(registrationRepository.findByAdNumber("123456")).thenReturn(participant);
        participant.setStatus(Status.TO_BE_PAID);
        boolean result = service.checkToken("123456", "3179c3:ec6e28a7ef:-8000");

        assertFalse(result);
        assertEquals(participant.getStatus(), Status.TO_BE_PAID);

        Mockito.verify(confirmationLinkRepository).findByAdNumber("123456");
        Mockito.verify(registrationRepository).findByAdNumber("123456");

        Mockito.verifyNoMoreInteractions(confirmationLinkRepository);
        Mockito.verifyNoMoreInteractions(registrationRepository);
    }

    @Test
    public void checkTokenForNonExistingConfirmationLinkReturnsFalse(){
        Mockito.when(confirmationLinkRepository.findByAdNumber("123456")).thenReturn(null);
        Mockito.when(registrationRepository.findByAdNumber("123456")).thenReturn(participant);
        boolean result = service.checkToken("123456", "3179c3:ec6e28a7ef:-8000");

        assertFalse(result);

        Mockito.verify(confirmationLinkRepository).findByAdNumber("123456");

        Mockito.verifyNoMoreInteractions(confirmationLinkRepository);
        Mockito.verifyNoMoreInteractions(registrationRepository);
    }

    @Test
    public void testDummy(){
        System.out.println(service.generateToken());

    }
}
