package com.realdolmen.chiro.controller;

import java.io.IOException;
import java.util.Arrays;

import com.realdolmen.chiro.integration.IntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.realdolmen.chiro.TestApplication;

/**
 * Base test class with the right configuration to use Spring MockMvc.
 *
 * Tests wishing to use MockMvc should inherit this class,
 * and utilize the mockMvc() method to do their requests.
 *
 * JSON conversion adapted from [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestApplication.class}) // Spring Boot config (includes component scan)
@Transactional // Enables rollback after each test.
@TestPropertySource(locations="classpath:application-test.properties") // Different set of properties to set H2 as DB.
public abstract class MockMvcTest extends IntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter =
                Arrays.stream(converters)
                      .filter(
                          hmc -> hmc instanceof MappingJackson2HttpMessageConverter
                      )
                      .findAny()
                      .get();

        Assert.assertNotNull(
                "the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter
        );
    }

    @Before
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders
                            .webAppContextSetup(this.context)
                            .build();
    }

    /**
     * Convenience method for subclasses to access the MockMvc instance.
     */
    MockMvc mockMvc(){
        return this.mockMvc;
    }

    /**
     * Convenience method for subclasses to convert an object to JSON format.
     */
    String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter
                .write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
