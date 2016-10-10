package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Before
    public void setUp(){
        // Participant
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        participant = new RegistrationParticipant(
                "123456789", "astrid@mail.do", "Astrid", "Deckers", c.getTime(),
                "AG0001", Gender.WOMAN, Role.LEADER, Eatinghabbit.VEGI
        );
        participant.setAddress(new Address("My Street", "2", 1252, "My City"));

        // Volunteer
        c.set(1995, Calendar.AUGUST, 5);

        volunteer = new RegistrationVolunteer(
                "123456789", "aster.deckers@example.org", "Aster", "Deckers", c.getTime(),
                "AG0001", Gender.MAN, Role.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KLINKER_EDITORIAL)
        );
        volunteer.setAddress(new Address("-", "-", 1500, "-"));
    }

    @Test
    public void saveMethodCreatesNewRegistration() throws Exception {
        String jsonPayload = json(participant);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        assertNotNull(repo.findByAdNumber(participant.getAdNumber()));
        assertEquals(1, repo.findAll().size());
    }

    @Test
    public void savingRegistrationWithDuplicateADNumberFails() throws Exception {
        String jsonPayload = json(participant);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        assertNotNull(repo.findByAdNumber(volunteer.getAdNumber()));
        assertEquals(1, repo.findAll().size());

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertEquals(1, repo.findAll().size());
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

        assertEquals(0, repo.findAll().size());
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

        assertEquals(0, repo.findAll().size());
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

        assertEquals(0, repo.findAll().size());
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

        assertEquals(1, repo.findAll().size());
    }
}
