package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.service.ChiroUnitService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UnitControllerMockitoTest {
    @InjectMocks
    private UnitController unitController;

    @Mock
    private ChiroUnitService chiroUnitService;

    private final static String TEST_STAMLETTERS = "LEG ";
    private final static String TEST_STAMCIJFERS = "0000";
    private final static String TEST_STAMNR = "LEG /0000";

    @After
    public void verifyStrict() {
        Mockito.verifyNoMoreInteractions(chiroUnitService);
    }

    @Test
    public void findRegisteredParticipantsByGroupReturnsListOfParticipants() {
        List<RegistrationParticipant> participants = new ArrayList<>();
        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        participants.add(registrationParticipant);

        Mockito.when(chiroUnitService.findParticipantsByChiroUnit(TEST_STAMNR)).thenReturn(participants);
        List<RegistrationParticipant> unitParticipants = unitController.findRegisteredParticipantsByGroup(TEST_STAMNR);
        Assert.assertSame(participants, unitParticipants);
        Assert.assertEquals(participants.size(), unitParticipants.size());
        Mockito.verify(chiroUnitService, times(1)).findParticipantsByChiroUnit(TEST_STAMNR);
    }

    @Test
    public void findRegisteredVolunteersByGroupReturnsListOfParticipants() {
        List<RegistrationVolunteer> volunteers = new ArrayList<>();
        RegistrationVolunteer registrationVolunteer = new RegistrationVolunteer();
        volunteers.add(registrationVolunteer);

        Mockito.when(chiroUnitService.findVolunteersByChiroUnit(TEST_STAMNR)).thenReturn(volunteers);
        List<RegistrationVolunteer> unitVolunteers = unitController.findRegisteredVolunteersByGroup(TEST_STAMNR);
        Assert.assertSame(volunteers, unitVolunteers);
        Assert.assertEquals(volunteers.size(), unitVolunteers.size());
        Mockito.verify(chiroUnitService, times(1)).findVolunteersByChiroUnit(TEST_STAMNR);
    }
}
