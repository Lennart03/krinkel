package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OverviewControllerTest {

    private RegistrationParticipant participantAstrid;

    private RegistrationVolunteer participantAster;

    private List<RegistrationParticipant> participants = new ArrayList<>();

    private List<RegistrationVolunteer> volunteers = new ArrayList<>();

    private int[] participantsAndVolunteers = new int[2];

    private List[] participantsInfo = new List[2];

    @InjectMocks
    private OverviewController controller;

    @Mock
    private RegistrationParticipantService registrationParticipantService;

    @Before
    public void init() {
        // Participant Astrid
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        participantAstrid = new RegistrationParticipant(
                "123456789", "astrid@mail.do", "Astrid", "Deckers", c.getTime(),
                "AG0001", Gender.WOMAN, Role.LEADER, Eatinghabbit.VEGI
        );
        participantAstrid.setAddress(new Address("My Street", "2", 1252, "My City"));

        // Participant Aster
        c.set(1993, Calendar.AUGUST, 15);

        participantAster = new RegistrationVolunteer(
                "123456789", "aster.deckers@example.org", "Aster", "Deckers", c.getTime(),
                "AG0001", Gender.MAN, Role.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KRINKEL_EDITORIAL)
        );
        participantAster.setAddress(new Address("-", "-", 1500, "-"));

        volunteers.add(participantAster);
        participants.add(participantAstrid);


    }

    @Test
    public void getParticipantsByUnit() {
        Mockito.when(registrationParticipantService.findParticipantsByGroup("AG0001")).thenReturn(participants);
        Mockito.when(registrationParticipantService.findVolunteersByGroup("AG0001")).thenReturn(volunteers);

        participantsAndVolunteers[0] = participants.size();
        participantsAndVolunteers[1] = volunteers.size();

        Assert.assertSame(controller.findParticipants("AG0001")[0], participantsAndVolunteers[0]);
        Assert.assertSame(controller.findParticipants("AG0001")[1], participantsAndVolunteers[1]);
    }

    @Test
    public void getParticipuntsInfo() {
        Mockito.when(registrationParticipantService.findParticipantsByGroup("AG0001")).thenReturn(participants);
        Mockito.when(registrationParticipantService.findVolunteersByGroup("AG0001")).thenReturn(volunteers);

        participantsInfo[0] = participants;
        participantsInfo[1] = volunteers;

        Assert.assertSame(controller.participantsInfo("AG0001")[0], participantsInfo[0]);
        Assert.assertSame(controller.participantsInfo("AG0001")[1], participantsInfo[1]);
    }
}
