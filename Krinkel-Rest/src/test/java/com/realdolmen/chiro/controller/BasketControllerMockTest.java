package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.component.RegistrationBasketComponent;
import com.realdolmen.chiro.config.SecurityFilterTestConfig;
import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.dto.EmailHolder;
import com.realdolmen.chiro.service.BasketService;
import com.realdolmen.chiro.spring_test.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by SWLBB72 on 12/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityFilterTestConfig.class})
public class BasketControllerMockTest extends MockMvcTest {
    private static final String STAMNUMMER = "AG0001";
    private RegistrationParticipant participant;

    @Mock
    private RegistrationBasketComponent registrationBasketComponent;

    @Autowired
    @InjectMocks
    private BasketService basketService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        participant = new RegistrationParticipant(
                "386283", "astrid@mail.do", "Astrid", "Deckers", c.getTime(),
                STAMNUMMER, Gender.WOMAN, EventRole.LEADER, Eatinghabbit.VEGI, "aster.deckers@example.org"
        );
        participant.setAddress(new Address("My Street", "2", 1252, "My City"));
        participant.setStatus(Status.TO_BE_PAID);


    }

    @Test
    public void addParticipantToBasketTest() throws Exception {
        String jsonPayload = json(participant);

        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/basket")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(registrationBasketComponent, times(1)).addUserToBasket(any());
    }

    @Test
    public void addInvalidParticipantToBasketTest() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        RegistrationParticipant invalidParticipant = new RegistrationParticipant(
                "386283", null, "Astrid", "Deckers", c.getTime(),
                STAMNUMMER, Gender.WOMAN, EventRole.LEADER, Eatinghabbit.VEGI, "aster.deckers@example.org"
        );
        invalidParticipant.setAddress(new Address("My Street", "2", 1252, "My City"));
        invalidParticipant.setStatus(Status.TO_BE_PAID);
        String jsonPayload = json(invalidParticipant);
        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/basket")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void addNullToBasketTest() throws Exception {
        String jsonPayload = json(null);
        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/basket")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    public void getParticipantsFromBasketTest() throws Exception {
        mockMvc()
                .perform(
                        MockMvcRequestBuilders.get("/api/basket")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        verify(registrationBasketComponent, times(1)).getUsersInBasket();
    }

    @Test
    public void deleteParticipantFromBasketTest() throws Exception {
        when(registrationBasketComponent.getUsersInBasket()).thenReturn(Arrays.asList(participant));
        mockMvc()
                .perform(
                        MockMvcRequestBuilders.get("/api/basket/delete/{adNumber}", 386283)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        verify(registrationBasketComponent, times(1)).removeUserFromBasket(any());
    }

    @Test
    public void deleteInvalidParticipantFromBasketTest() throws Exception {
        mockMvc()
                .perform(
                        MockMvcRequestBuilders.get("/api/basket/delete/{adNumber}", "invalid")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void getRegistrationMailFromBasketTest() throws Exception {
        mockMvc()
                .perform(
                        MockMvcRequestBuilders.get("/api/basket/mail")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        verify(registrationBasketComponent, times(1)).getDestinationMail();
    }

    @Test
    public void setRegistrationMailToBasketTest() throws Exception {
        when(registrationBasketComponent.getUsersInBasket()).thenReturn(Arrays.asList(participant));
        final String emailSubscriber = "shenno@mail.do";
        String jsonPayload = json(new EmailHolder(emailSubscriber));
        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/basket/mail")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        verify(registrationBasketComponent, times(1)).setDestinationMail(emailSubscriber);
    }

    @Test
    public void setInvalidRegistrationMailToBasketTest() throws Exception {
        String jsonPayload = json(new EmailHolder("notanemail"));
        mockMvc()
                .perform(
                        MockMvcRequestBuilders.post("/api/basket/mail")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void payBasketTest() throws Exception {
        when(registrationBasketComponent.getUsersInBasket()).thenReturn(Arrays.asList(participant));
        MvcResult mvcResult = mockMvc()
                .perform(
                        MockMvcRequestBuilders.get("/api/basket/pay")
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        assertNotNull(mvcResult.getResponse().getHeader("Location"));

    }
}
