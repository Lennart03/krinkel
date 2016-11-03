package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import com.realdolmen.chiro.domain.mothers.RegistrationVolunteerMother;
import com.realdolmen.chiro.spring_test.MockMvcTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Calendar;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@Ignore
public class RegistrationVolunteerControllerMockTest extends MockMvcTest {

    private RegistrationVolunteer volunteer;
    private RegistrationParticipant participant;

    @Autowired
    private RegistrationVolunteerRepository repo;

    private int nVolunteers = 0;

    @Before
    public void setUp(){
        // Participant
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        participant = new RegistrationParticipant(
                "386283", "astrid@mail.any", "Maia", "Van Op Beeck", c.getTime(),
                "AG0001", Gender.WOMAN, EventRole.ASPI, Eatinghabbit.FISHANDMEAT
        );
        participant.setAddress(new Address("My Street", "2", 1252, "My City"));

        c.set(1995, Calendar.AUGUST, 5);

        volunteer = new RegistrationVolunteer(
                "386283", "aster.deckers@example.org", "Aster", "Deckers", c.getTime(),
                "AG0001", Gender.MAN, EventRole.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KRINKEL_EDITORIAL)
        );
        volunteer.setAddress(new Address("-", "-", 1500, "-"));

        this.nVolunteers = repo.findAll().size();
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
        assertEquals(nVolunteers+1, repo.findAll().size());
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
        assertEquals(nVolunteers+1, repo.findAll().size());

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/volunteers")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        assertEquals(nVolunteers+1, repo.findAll().size());
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

        assertEquals(nVolunteers, repo.findAll().size());
    }

    @Test
    public void savingRegistrationReturnsLocationHeaderOnSuccess() throws Exception {
        String jsonPayload = json(volunteer);

        MvcResult mvcResult = mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/volunteers")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andReturn();

        assertNotNull(mvcResult.getResponse().getHeader("Location"));
    }


    @Test
    public void savingVolunteerWithManyPreCampsSucceeds() throws Exception {
        String jsonPayload = json(RegistrationVolunteerMother.createRegistrationVolunteerWithManyPreCamps());

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/volunteers")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
