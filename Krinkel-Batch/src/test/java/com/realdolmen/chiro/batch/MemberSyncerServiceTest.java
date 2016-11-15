package com.realdolmen.chiro.batch;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.configuration.ApplicationConfiguration;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

@ContextConfiguration(classes = {ApplicationConfiguration.class})
public class MemberSyncerServiceTest extends SpringIntegrationTest {

    @Autowired
    private TaskScheduler scheduler;

    @Mock
    private ChiroUserAdapter adapter;

    @Mock
    private RegistrationParticipantService participantService;

    @InjectMocks
    private MemberSyncerService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void schedulerIsConfiguredCorrectly() {
        assertNotNull(scheduler);
    }

    @Test
    public void syncUsersToChiroDBSendsEveryFoundParticipantToUserAdapter() throws Exception {
        //setup
        List<RegistrationParticipant> participants = new ArrayList<>();
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());

        Mockito.when(participantService.getSyncReadyParticipants()).thenReturn(participants);

        //actual test
        service.syncUsersToChiroDB();
        Mockito.verify(adapter, times(participants.size())).syncUser(any(RegistrationParticipant.class));
        Mockito.verify(participantService, times(participants.size())).setUserToSynced(any(String.class));
    }

    @Test
    public void syncUsersToChiroDBDoesNothingWhenNoParticipantsWereFound() {
        Mockito.when(participantService.getSyncReadyParticipants()).thenReturn(new ArrayList<>());

        service.syncUsersToChiroDB();
        Mockito.verifyNoMoreInteractions(adapter);
    }

    @Test
    public void syncUsersToChiroDBDoesNothingWhenSyncingThrowsError() throws Exception {
        //setup
        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        List<RegistrationParticipant> participants = new ArrayList<>();
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());
        participants.add(registrationParticipant);


        Mockito.when(participantService.getSyncReadyParticipants()).thenReturn(participants);
        Mockito.doThrow(new Exception()).when(adapter).syncUser(registrationParticipant);

        service.syncUsersToChiroDB();
        Mockito.verify(adapter, times(participants.size())).syncUser(any(RegistrationParticipant.class));
        Mockito.verify(participantService, times(participants.size() - 1)).setUserToSynced(any(String.class));
    }


}
