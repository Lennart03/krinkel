package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.Application;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base test class with the right configuration to use Spring MockMvc.
 *
 * Tests wishing to use MockMvc should inherit this class,
 * and utilize the mockMvc() method to do their requests.
 */
@WebAppConfiguration
@ContextConfiguration(classes={Application.class}) // Spring Boot config (includes component scan)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations="classpath:test-application.properties") // Different set of properties to set H2 as DB.
public abstract class MockMvcTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    MockMvc mockMvc(){
        return this.mockMvc;
    }
}
