package com.realdolmen.chiro.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class CASControllerMockTest extends MockMvcTest{

    @Test
    public void anonymousUserGetsRedirectedToChiroCasServer() throws Exception {
        mockMvc().perform(
                        get("/")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
