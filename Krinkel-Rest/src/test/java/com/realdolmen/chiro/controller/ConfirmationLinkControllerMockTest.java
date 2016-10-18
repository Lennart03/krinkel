package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.ConfirmationLink;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.mothers.RegistrationParticipantMother;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.service.ConfirmationLinkService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ConfirmationLinkControllerMockTest extends MockMvcTest{

    @Autowired
    private ConfirmationLinkService linkService;

    @Autowired
    private RegistrationParticipantRepository participantRepository;

    private RegistrationParticipant participant;
    private ConfirmationLink confirmationLink;

    @Before
    public void setUp() throws Exception {
        participant = RegistrationParticipantMother.createBasicRegistrationParticipant();
        participant.setStatus(Status.PAID);

        participantRepository.save(participant);
        confirmationLink = linkService.createConfirmationLink(participant.getAdNumber());
    }

    @Test
    public void correctTokenInLinkGivesSuccessConfirmationPage() throws Exception {
        mockMvc().perform(
                    get("/confirmation")
                        .param("ad", participant.getAdNumber())
                        .param("token", confirmationLink.getToken())
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(view().name("confirmation"))
                .andExpect(model().attribute("success", true));
    }

    @Test
    public void incorrectTokenInLinkGivesErrorConfirmationPage() throws Exception {
        // Make a wrong token by appending the correct token with some rubbish data.
        String wrongToken = "AAA" + confirmationLink.getToken() + "AAA";

        mockMvc().perform(
                get("/confirmation")
                        .param("ad", participant.getAdNumber())
                        .param("token", wrongToken)
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(view().name("confirmation"))
                .andExpect(model().attribute("success", false));
    }

    @Test
    public void emptyTokenInLinkGivesErrorConfirmationPage() throws Exception {
        String emptyToken = "";

        mockMvc().perform(
                get("/confirmation")
                        .param("ad", participant.getAdNumber())
                        .param("token", emptyToken)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(view().name("confirmation"))
                .andExpect(model().attribute("success", false));
    }
}
