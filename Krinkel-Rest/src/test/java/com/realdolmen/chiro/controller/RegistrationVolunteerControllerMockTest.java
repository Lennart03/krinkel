package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Calendar;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

public class RegistrationVolunteerControllerMockTest extends MockMvcTest {

    private RegistrationVolunteer volunteer;
    private RegistrationParticipant participant;

    @Autowired
    private RegistrationVolunteerRepository repo;

    @Before
    public void setUp(){
        // Participant
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        participant = new RegistrationParticipant(
                "123456789", "astrid@mail.any", "Maia", "Van Op Beeck", c.getTime(),
                "AG0001", Gender.WOMAN, Role.ASPI, Eatinghabbit.FISHANDMEAT
        );
        participant.setAddress(new Address("My Street", "2", 1252, "My City"));

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
    public void resultingJSONShouldUseCorrectDateFormat() throws Exception {
        mockMvc().perform(MockMvcRequestBuilders.get("/api/volunteers"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"birthdate\":\"1995-08-05\",")));
    }

    @Test
    public void saveMethodCreatesNewRegistration() throws Exception {
        String jsonPayload = json(volunteer);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/volunteers")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        assertNotNull(repo.findByAdNumber(volunteer.getAdNumber()));
        assertEquals(1, repo.findAll().size());
    }

    @Test
    public void savingRegistrationWithDuplicateADNumberFails() throws Exception {
        String jsonPayload = json(volunteer);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/volunteers")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        assertNotNull(repo.findByAdNumber(volunteer.getAdNumber()));
        assertEquals(1, repo.findAll().size());

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/volunteers")
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
                        MockMvcRequestBuilders.post("/api/volunteers")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void savingRegistrationForParticipantInsteadOfVolunteerFails() throws Exception {
        String jsonPayload = json(participant);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/volunteers")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertEquals(0, repo.findAll().size());
    }
}
