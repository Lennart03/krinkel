package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Calendar;

public class RegistrationParticipantControllerMockTest extends MockMvcTest {

    private RegistrationParticipant participant;

    @Before
    public void setUp(){
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        participant = new RegistrationParticipant(
                "123456789", "Astrid", "Deckers", c.getTime(),
                "AG0001", Gender.WOMAN, Role.LEADER, Eatinghabbit.VEGI
        );
        participant.setAddress(new Address("My Street", "2", 1252, "My City"));
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

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/participants")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
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
    }
}
