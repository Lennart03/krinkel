package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.config.SecurityFilterTestConfig;
import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.service.UserService;
import com.realdolmen.chiro.spring_test.MockMvcTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//import java.util.Calendar;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Calendar;

@ContextConfiguration(classes = {SecurityFilterTestConfig.class})
public class RegistrationParticipantControllerMockTest extends MockMvcTest {

	private static final String STAMNUMMER = "AG0001";
    private RegistrationParticipant participant;
    private RegistrationVolunteer volunteer;
    

    @Autowired
    private RegistrationParticipantRepository repo;

    private int nParticipants = 0;
    

    @Before
    public void setUp(){
         //Participant
        MockitoAnnotations.initMocks(this);

        // Participant
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        participant = new RegistrationParticipant(
                "386283", "astrid@mail.do", "Astrid", "Deckers", c.getTime(),
                STAMNUMMER, Gender.WOMAN, EventRole.LEADER, Eatinghabbit.VEGI, "aster.deckers@example.org"
        );
        participant.setAddress(new Address("My Street", "2", 1252, "My City"));

        // Volunteer
        c.set(1995, Calendar.AUGUST, 5);

        volunteer = new RegistrationVolunteer(
                "386283", "aster.deckers@example.org", "Aster", "Deckers", c.getTime(),
                STAMNUMMER, Gender.MAN, EventRole.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KRINKEL_EDITORIAL), "aster.deckers@example.org"
        );
        volunteer.setAddress(new Address("-", "-", 1500, "-"));
//    	participant = RegistrationParticipantMother.createBasicRegistrationParticipant();
//    	volunteer = RegistrationVolunteerMother.createBasicRegistrationVolunteer();

        this.nParticipants = repo.findAll().size();

        //TODO Use this in some other test class where the actual security constraints are tested.
        //        Authentication authentication = Mockito.mock(Authentication.class);
        //        when(authentication.isAuthenticated()).thenReturn(true);
        //        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        //        when(securityContext.getAuthentication()).thenReturn(authentication);
        //        SecurityContextHolder.setContext(securityContext);
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
    public void savingRegistrationWithDuplicateADNumberUpdatesData() throws Exception {
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
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

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
