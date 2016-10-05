package com.realdolmen.chiro.controller;

import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class RegistrationVolunteerControllerMockTest extends MockMvcTest {

    @Test
    public void resultingJSONShouldUseCorrectDateFormat() throws Exception {
        mockMvc().perform(get("/api/volunteers"))
                .andExpect(content().string(containsString("\"birthdate\":\"1995-08-05\",")));
    }
}
