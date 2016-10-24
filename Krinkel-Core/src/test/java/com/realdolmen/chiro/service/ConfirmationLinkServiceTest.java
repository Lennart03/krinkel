package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.exception.DuplicateEntryException;
import com.realdolmen.chiro.repository.ConfirmationLinkRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static org.junit.Assert.*;

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
    public void createConfirmationLinkCreatesNewEntity() throws DuplicateEntryException {
        Mockito.when(confirmationLinkRepository.findByAdNumber("123456")).thenReturn(null);
        Mockito.when(confirmationLinkRepository
                .save(Mockito.any(ConfirmationLink.class)))
                .thenAnswer(new Answer<ConfirmationLink>() {
                    @Override
                    public ConfirmationLink answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        ConfirmationLink link = (ConfirmationLink) args[0];
                        link.setId(42L);
                        return link;
                    }
                });

        ConfirmationLink confirmationLink = service.createConfirmationLink("123456");

        assertNotNull(confirmationLink);
        assertNotNull(confirmationLink.getId());
        assertNotNull(confirmationLink.getToken());
        assertEquals("123456", confirmationLink.getAdNumber());

        Mockito.verify(confirmationLinkRepository).findByAdNumber("123456");
        Mockito.verify(confirmationLinkRepository).save(Mockito.any(ConfirmationLink.class));

        Mockito.verifyNoMoreInteractions(confirmationLinkRepository);
        Mockito.verifyNoMoreInteractions(registrationRepository);
    }

    @Test
    public void createConfirmationLinkThrowsExceptionForDuplicateADNumber(){
        Mockito.when(confirmationLinkRepository.findByAdNumber("123456")).thenReturn(confirmationLink);

        try {
            service.createConfirmationLink("123456");
        }
        catch(DuplicateEntryException ex){}

        Mockito.verify(confirmationLinkRepository).findByAdNumber("123456");

        Mockito.verifyNoMoreInteractions(confirmationLinkRepository);
        Mockito.verifyNoMoreInteractions(registrationRepository);
    }
}
