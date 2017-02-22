package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.CampGround;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by JCPBB69 on 15/12/2016.
 */
public class ChiroUnitRepositoryTest extends SpringIntegrationTest{
    @Autowired
    private ChiroUnitRepository chiroUnitRepository;

    @Test
    public void findAllGewesten(){
        List<ChiroUnit> allGewesten = chiroUnitRepository.findAllGewesten();
        assertEquals(72,allGewesten.size());
    }

    @Test
    public void findAllGroups(){
        List<RawChiroUnit> allGroups = chiroUnitRepository.findAll();
        assertEquals(904,allGroups.size());
    }

    @Test
    public void testCountParticipantFromVerbond(){
        int count = chiroUnitRepository.countParticipantsByVerbond("AG /0000");
        assertEquals(4, count);
    }

    @Test
    public void testCountVolunteerFromVerbond(){
        int count = chiroUnitRepository.countVolunteersByVerbond("AG /0000");
        assertEquals(1, count);
    }

    @Test
    public void testCountParticipantFromGewest(){
        int count = chiroUnitRepository.countParticipantsByGewest("AG /0400");
        assertEquals(3, count);
    }

    @Test
    public void testCountVolunteerFromGewest(){
        int count = chiroUnitRepository.countVolunteersByGewest("AG /0400");
        assertEquals(1, count);
    }

    @Test
    public void testCountParticipantFromGroep(){
        int count = chiroUnitRepository.countParticipantsByGroep("AG /0103");
        assertEquals(3, count);
    }

    @Test
    public void testCountVolunteerFromGroep(){
        int count = chiroUnitRepository.countVolunteersByGroep("AG /0103");
        assertEquals(1, count);
    }

    @Test
    public void testReturnParticipantsByGroepInAList(){
        List<RegistrationParticipant> participants = new ArrayList<>();
        participants = chiroUnitRepository.returnParticipantsByGroep("AG /0103",true);
        int size = participants.size();
        assertEquals(3, size);
    }

    @Test
    public void testReturnVolunteersByGroepInAList(){
        List<RegistrationVolunteer> volunteers = new ArrayList<>();
        volunteers = chiroUnitRepository.returnVolunteersByGroep("AG /0103");
        int size = volunteers.size();
        assertEquals(1, size);
    }

    @Test
    public void testIfReturnedParticipantHasCorrectData(){
        List<RegistrationParticipant> participants = new ArrayList<>();
        participants = chiroUnitRepository.returnParticipantsByGroep("AG /0103",true);
        RegistrationParticipant participant = participants.get(1);
        assertEquals("Ma", participant.getFirstName());
        assertEquals("Flodder", participant.getLastName());
        assertEquals("email@test.be", participant.getEmail());
        assertEquals("345678", participant.getAdNumber());
    }

    @Test
    public void testIfReturnedVolunteerHasCorrectData(){
        List<RegistrationVolunteer> participants = new ArrayList<>();
        participants = chiroUnitRepository.returnVolunteersByGroep("AG /0103");
        RegistrationParticipant participant = participants.get(0);
        assertEquals("Jos", participant.getFirstName());
        assertEquals("Flodder", participant.getLastName());
        assertEquals("email@test.be", participant.getEmail());
        assertEquals("876543", participant.getAdNumber());
    }

    @Test
    public void testIfReturnedVolunteerHasCorrectDataFoutief(){
        List<RegistrationVolunteer> participants = new ArrayList<>();
        participants = chiroUnitRepository.returnVolunteersByGroep("AG /0103");
        RegistrationParticipant participant = participants.get(0);
        assertEquals("Jos", participant.getFirstName());
        assertEquals("Flodder", participant.getLastName());
        assertEquals("email@test.be", participant.getEmail());
        assertNotEquals("87654", participant.getAdNumber());
    }

    @Test
    public void testGetVerbonden(){

        int aantalInternationaalCONFIRMED = chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName("Internationaal"), Status.CONFIRMED);
        assertEquals(1, aantalInternationaalCONFIRMED);
        int aantalInternationaalPAID = chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName("Internationaal"), Status.PAID);
        assertEquals(0, aantalInternationaalPAID);
        int aantalantwerpenCONFIRMEd= chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName("Antwerpen"), Status.CONFIRMED);
        assertEquals(0, aantalantwerpenCONFIRMEd);

        List<RegistrationVolunteer> registrationVolunteers = chiroUnitRepository.returnVolunteersByCampGround(CampGround.INTERNATIONAAL);
        System.err.println("**************************************************");
        System.err.println(registrationVolunteers);
        assertEquals(1, registrationVolunteers.size());
    }

    @Test
    public void testcountParticipantsInternationaal(){
        int countConfirmed = chiroUnitRepository.countParticipantsInternationaal(Status.CONFIRMED);
        int countPaid = chiroUnitRepository.countParticipantsInternationaal(Status.PAID);
        int others = chiroUnitRepository.countParticipantsInternationaal(Status.TO_BE_PAID) + chiroUnitRepository.countParticipantsInternationaal(Status.CANCELLED);

        assertEquals(1,countConfirmed);
        assertEquals(1,countPaid);
        assertEquals(0,others);
    }
    @Test
    public void testcountParticipantsByGroep(){
        int i = chiroUnitRepository.countParticipantsByGroep("AG /0103", Status.CONFIRMED, false);
        assertEquals(2,i);
        i = chiroUnitRepository.countParticipantsByGroep("AG /0103", Status.PAID, false);
        assertEquals(0,i);
        i = chiroUnitRepository.countParticipantsByGroep("AG /0103", Status.TO_BE_PAID, false);
        assertEquals(1,i);
        i = chiroUnitRepository.countParticipantsByGroep("AG /0103", Status.CANCELLED, false);
        assertEquals(0,i);

    }
}
