package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Basic test class with the right configuration to use Spring MockMvc.
 *
 * TODO: Change creation of dummy data to something not using the em?
 */
@Transactional // To use Entity Manager.
public class DummyControllerMockTest extends MockMvcTest{

    @PersistenceContext
    EntityManager em;

    Long id;

    @Before
    public void setUp(){
        RegistrationParticipant p = new RegistrationParticipant(
                "123456",
                "Hermione",
                "Granger",
                new Date(),
                "AG0104",
                Gender.WOMAN,
                Role.ASPI,
                Eatinghabbit.VEGI
        );

        p.setAddress(new Address());
        em.persist(p);
        id = p.getId();
    }

    @Test
    public void testGetDummyEntity(){
        // Paranoia check: the transaction includes the @Before and @After methods.
        // This means an entity added in the @Before should be able to be retrieved in this method.
        Assert.assertNotNull(em.find(RegistrationParticipant.class, id));
    }

    @Test
    public void testMockMvc() throws Exception{
        mockMvc().perform(MockMvcRequestBuilders.get("/dummy/"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Hello World")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
