package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.Function;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class RegistrationParticipantControllerTest {
    private static String URL_PREFIX = "http://localhost:8080";

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void restServiceShouldSaveNewParticipant() {
        RegistrationParticipant participant = new RegistrationParticipant();
        participant.setFirstName("NICK");
        participant.setLastName("HANOT");
        participant.setStamnumber("STAMNUMMER");
        participant.setAdNumber("ADNUMMER");
        participant.setFunction(Function.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());
        restTemplate.postForEntity(URL_PREFIX + "/api/participants", participant, Void.class);
    }

    @Test
    @Ignore
    public void restServiceCanNotSave2SameParticipant() {
        RegistrationParticipant participant = new RegistrationParticipant();
        participant.setFirstName("NICK");
        participant.setLastName("HANOT");
        participant.setStamnumber("STAMNUMMER");
        participant.setAdNumber("ADNUMMER");
        participant.setFunction(Function.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());

        ResponseEntity<?> response;
        HttpStatus status;

        response = restTemplate.postForEntity(URL_PREFIX + "/api/participants", participant, ResponseEntity.status(201).getClass());
        status = response.getStatusCode();
        assertEquals(status.value(), 201);
        response = restTemplate.postForEntity(URL_PREFIX + "/api/participants", participant, ResponseEntity.badRequest().getClass());
        status = response.getStatusCode();
        assertEquals(status.value(), 400);
    }
}
