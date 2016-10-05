package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationVolunteerControllerMockTest extends MockMvcTest {

    private RegistrationVolunteer volunteer;

    @Before
    public void setUp(){
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        volunteer = new RegistrationVolunteer(
                "123456789", "Aster", "Deckers", c.getTime(),
                "AG0001", Gender.MAN, Role.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KLINKER_EDITORIAL)
        );
        volunteer.setAdress(new Adress());
    }

    @Test
    public void resultingJSONShouldUseCorrectDateFormat() throws Exception {
        mockMvc().perform(get("/api/volunteers"))
                .andExpect(content().string(containsString("\"birthdate\":\"1995-08-05\",")));
    }

    @Test
    public void saveMethodCreatesNewRegistration() throws Exception {
        String jsonPayload = json(volunteer);

        mockMvc()
                .perform(
                        post("/api/volunteers")
                            .contentType(APPLICATION_JSON_UTF8)
                            .content(jsonPayload))
                .andExpect(status().is2xxSuccessful());
    }
}
