package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.units.ChiroUnit;
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

    @Test
    public void testgetRegistrationVolunteersByCampground(){
        List<RegistrationVolunteer> internationaal = verbondenService.getRegistrationVolunteersByCampground("Internationaal");
        System.err.println("**************************************************");
        System.err.println(internationaal);
        assertEquals(1, internationaal.size());
    }

    @Test
    public void test123(){
        List<ChiroUnit> verbonden = verbondenService.getVerbonden();
        System.err.println("**************************************************");
        for (ChiroUnit chiroUnit : verbonden) {
            System.err.println(chiroUnit);
        }


    }

    @Test
    public void testgetGroepen(){
        List<ChiroUnit> ag0400 = verbondenService.getGroepen("AG0400");
        System.err.println("INSIDE TEST testGetGroepen");
        for (ChiroUnit chiroUnit : ag0400) {
            System.err.println(chiroUnit);
        }
    }

    @Test
    public void testgetRegistrationParticipants() {
        List<RegistrationParticipant> ints = verbondenService.getRegistrationParticipants("INT");
        for (RegistrationParticipant anInt : ints) {
            System.err.println("participant: " + anInt.getFirstName());
        }
        assertEquals(2, ints.size());
    }
}
