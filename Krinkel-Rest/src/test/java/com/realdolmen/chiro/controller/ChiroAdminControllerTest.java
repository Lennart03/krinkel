package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.spring_test.MockMvcTest;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


/**
 * Created by LVDBB73 on 9/12/2016.
 */
public class ChiroAdminControllerTest extends MockMvcTest {

    @Test
    public void retrieveAllAdminsTest() throws Exception {
        mockMvc().perform(
                get("/api/admins")
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void deleteSuccesTest() throws Exception {
        mockMvc().perform(
                delete("/api/admins/4")
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
