package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Calendar;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

public class RegistrationParticipantControllerMockTest extends MockMvcTest {

    private RegistrationParticipant participant;
    private RegistrationVolunteer volunteer;

    @Autowired
    private RegistrationParticipantRepository repo;

    private int nParticipants = 0;

    @Before
    public void setUp(){
        // Participant
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        participant = new RegistrationParticipant(
                "386283", "astrid@mail.do", "Astrid", "Deckers", c.getTime(),
                "AG0001", Gender.WOMAN, Role.LEADER, Eatinghabbit.VEGI
        );
        participant.setAddress(new Address("My Street", "2", 1252, "My City"));

        // Volunteer
        c.set(1995, Calendar.AUGUST, 5);

        volunteer = new RegistrationVolunteer(
                "386283", "aster.deckers@example.org", "Aster", "Deckers", c.getTime(),
                "AG0001", Gender.MAN, Role.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KRINKEL_EDITORIAL)
        );
        volunteer.setAddress(new Address("-", "-", 1500, "-"));


        this.nParticipants = repo.findAll().size();
    }

    @Test
    public void saveMethodCreatesNewRegistration() throws Exception {
        String jsonPayload = json(participant);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        assertNotNull(repo.findByAdNumber(participant.getAdNumber()));
        assertEquals(nParticipants+1, repo.findAll().size());
    }

    @Test
    public void savingRegistrationWithDuplicateADNumberFails() throws Exception {
        String jsonPayload = json(participant);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        assertNotNull(repo.findByAdNumber(volunteer.getAdNumber()));
        assertEquals(nParticipants+1, repo.findAll().size());

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertEquals(nParticipants+1, repo.findAll().size());
    }

    @Test
    public void savingEmptyRegistrationFails() throws Exception {
        String jsonPayload = "";

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertEquals(nParticipants, repo.findAll().size());
    }

    @Test
    public void savingRegistrationFromMalformedJSONFails() throws Exception {
        String jsonPayload = "{ {[{{]{}";

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertEquals(nParticipants, repo.findAll().size());
    }

    @Test
    public void savingRegistrationFromIncompleteJSONFails() throws Exception {
        String jsonPayload = "{\n" +
                "         \"adNumber\": \"123456789\",\n" +
                "         \"firstName\": \"Aster\"}";

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertEquals(nParticipants, repo.findAll().size());
    }

    /**
     * It is possible to save a volunteer via the participants endpoint.
     * It's a feature!
     * @throws Exception
     */
    @Test
    public void savingRegistrationForVolunteerInsteadOfParticipantFails() throws Exception {
        String jsonPayload = json(volunteer);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload));
                //.andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertEquals(nParticipants+1, repo.findAll().size());
    }

    @Test
    public void savingRegistrationReturnsLocationHeaderOnSuccess() throws Exception {
        String jsonPayload = json(participant);

        MvcResult mvcResult = mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andReturn();

        assertNotNull(mvcResult.getResponse().getHeader("Location"));
    }
}
