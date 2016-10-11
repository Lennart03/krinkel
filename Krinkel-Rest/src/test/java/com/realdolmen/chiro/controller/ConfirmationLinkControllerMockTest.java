package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.ConfirmationLink;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.mothers.RegistrationParticipantMother;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.service.ConfirmationLinkService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class ConfirmationLinkControllerMockTest extends MockMvcTest{

    @Autowired
    private ConfirmationLinkService linkService;

    @Autowired
    private RegistrationParticipantRepository participantRepository;

    @Test
    public void correctTokenInLinkReturnsPayloadForSuccess() throws Exception {
        RegistrationParticipant participant = RegistrationParticipantMother.createBasicRegistrationParticipant();
        participant.setStatus(Status.PAID);

        participantRepository.save(participant);
        ConfirmationLink confirmationLink = linkService.createConfirmationLink(participant.getAdNumber());

        System.out.println(confirmationLink.getToken());

        mockMvc().perform(
                    get("/api/confirmation")
                        .param("ad", participant.getAdNumber())
                        .param("token", confirmationLink.getToken())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().string(containsString("\"success\" : true")));

    }
}
