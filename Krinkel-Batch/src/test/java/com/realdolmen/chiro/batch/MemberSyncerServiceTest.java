package com.realdolmen.chiro.batch;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.configuration.ApplicationConfiguration;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class MemberSyncerServiceTest {

    @Autowired
    private TaskScheduler scheduler;
    private boolean mockInitialized = false;

    @Mock
    private ChiroUserAdapter adapter;
    @Mock
    private RegistrationParticipantRepository repo;

    @InjectMocks
    private MemberSyncerService service;


    @Before
    public void init() {
        //initialize mockito (can only use one @RunWith)
        if (!mockInitialized) {
            MockitoAnnotations.initMocks(this);
            mockInitialized = true;
        }
    }


    @Test
    public void schedulerIsConfiguredCorrecly() {
        Assert.notNull(scheduler);
    }

    @Test
    public void syncUsersToChiroDBSendsEveryFoundParticipantToUserAdapter() {
        //setup
        List<RegistrationParticipant> participants = new ArrayList<RegistrationParticipant>();
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());
        participants.add(new RegistrationParticipant());

        Mockito.when(repo.findAll()).thenReturn(participants);

        //actual test
        service.syncUsersToChiroDB();
        Mockito.verify(adapter, times(participants.size())).syncUser(any(RegistrationParticipant.class));
    }

    @Test
    public void syncUsersToChiroDBDoesNothingWhenNoParticipantsWereFound() {
        Mockito.when(repo.findAll()).thenReturn(new ArrayList<RegistrationParticipant>());

        service.syncUsersToChiroDB();
        Mockito.verifyNoMoreInteractions(adapter);
    }
}
