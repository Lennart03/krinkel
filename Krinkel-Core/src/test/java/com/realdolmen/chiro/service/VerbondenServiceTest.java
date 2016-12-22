package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by JCPBB69 on 18/12/2016.
 */
@ContextConfiguration(classes={com.realdolmen.chiro.config.TestConfig.class})
public class VerbondenServiceTest extends SpringIntegrationTest {

    @Autowired
    private VerbondenService verbondenService;

    @Test
    @Ignore
    public void testGetParticipantsWithoutVolunteers(){
        List<RegistrationParticipant> ag0103Participants = verbondenService.getRegistrationParticipants("AG0103");
        assertEquals(2, ag0103Participants.size());
        List<RegistrationVolunteer> ag0103Volunteers = verbondenService.getRegistrationVolunteers("ag0103");
        assertEquals(1, ag0103Volunteers.size());
    }
}
