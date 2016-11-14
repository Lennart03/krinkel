package com.realdolmen.chiro.spring_test;

import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;

/**
 * Base test class with the right configuration to use Spring MockMvc.
 *
 * Tests wishing to use MockMvc should inherit this class,
 * and utilize the mockMvc() method to do their requests.
 *
 * JSON conversion adapted from [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
 *
 */
public abstract class MockMvcTest extends SpringIntegrationTest {

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
    protected MockMvc mockMvc(){
        return this.mockMvc;
    }

    /**
     * Convenience method for subclasses to convert an object to JSON format.
     */
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter
                .write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
