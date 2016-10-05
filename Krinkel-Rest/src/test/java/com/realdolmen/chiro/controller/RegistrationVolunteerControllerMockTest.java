package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Calendar;

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
    }
}
