package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
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

    private RegistrationParticipant participantAster;

    private List<RegistrationParticipant> participants = new ArrayList<>();

    @InjectMocks
    private OverviewController controller;

    @Mock
    private RegistrationParticipantRepository registrationRepository;

    @Before
    public void init(){
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
                new VolunteerFunction(VolunteerFunction.Preset.KLINKER_EDITORIAL)
        );
        participantAster.setAddress(new Address("-", "-", 1500, "-"));

        participants.add(participantAster);
        participants.add(participantAstrid);
    }

    @Test
    public void getParticipantsByUnit(){
        Mockito.when(registrationRepository.findParticipantsByGroup("AG0001")).thenReturn(participants);
        Assert.assertSame(registrationRepository.findParticipantsByGroup("AG0001").size(), participants.size());
    }
}