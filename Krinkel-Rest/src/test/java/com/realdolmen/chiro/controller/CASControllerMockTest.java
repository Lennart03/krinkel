package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.spring_test.MockMvcTest;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class CASControllerMockTest extends MockMvcTest {

    @Test
    public void anonymousUserGetsRedirectedToChiroCasServer() throws Exception {
        mockMvc().perform(
                        get("/")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
