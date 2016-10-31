package com.realdolmen.chiro.batch;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SendStatus;
import com.realdolmen.chiro.domain.mothers.RegistrationParticipantMother;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.service.EmailSenderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.realdolmen.chiro.domain.mothers.RegistrationParticipantMother.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class EmailBatchServiceTest {

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private RegistrationCommunicationRepository registrationCommunicationRepository;

    @Mock
    private RegistrationParticipantRepository registrationParticipantRepository;

    @InjectMocks
    private EmailBatchService emailBatchService;

    private List<RegistrationCommunication> allWaitingAndFailedCommunications;

    private List<RegistrationParticipant> allParticipants;

    @Before
    public void setUp(){
        allWaitingAndFailedCommunications = new ArrayList<>(Arrays.asList(
                new RegistrationCommunication("123456", SendStatus.WAITING, 0),
                new RegistrationCommunication("123457", SendStatus.WAITING, 0)
            )
        );

        allParticipants = new ArrayList<>();
        allParticipants.add(
                basicRegistrationParticipantBuilder()
                        .adNumber("123456")
                        .build()
        );

        allParticipants.add(
                basicRegistrationParticipantBuilder()
                        .adNumber("123457")
                        .build()
        );

        allParticipants.add(
                basicRegistrationParticipantBuilder()
                        .adNumber("123458")
                        .build()
        );

        Mockito.when(emailSenderService.sendMail(Mockito.any())).thenReturn(null);

        Mockito.when(registrationCommunicationRepository.findAllWaitingAndFailed())
               .thenReturn(allWaitingAndFailedCommunications);

    }

    @After
    public void tearDown(){
        Mockito.verifyNoMoreInteractions(emailSenderService);
        Mockito.verifyNoMoreInteractions(registrationCommunicationRepository);
        Mockito.verifyNoMoreInteractions(registrationParticipantRepository);
    }

    @Test
    public void sendEmailsSendsCorrectNumber(){
        emailBatchService.sendEmails();

        int nEmailsExpected = allWaitingAndFailedCommunications.size();

        Mockito.verify(emailSenderService, times(nEmailsExpected))
                .sendMail(Mockito.any());

        Mockito.verify(registrationCommunicationRepository).findAllWaitingAndFailed();
        Mockito.verify(registrationParticipantRepository, times(nEmailsExpected)).findByAdNumber(Mockito.anyString());
    }

    @Test
    public void emptyWaitingAndFailedQueueDoesNotTriggerEmailSenderService(){
        // Empty queue
        this.allWaitingAndFailedCommunications.clear();

        // Trigger batch
        emailBatchService.sendEmails();

        Mockito.verify(registrationCommunicationRepository).findAllWaitingAndFailed();
    }
}

