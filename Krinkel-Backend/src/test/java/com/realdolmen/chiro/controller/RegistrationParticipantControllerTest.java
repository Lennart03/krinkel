package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.Adress;
import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RegistrationParticipantControllerTest {
    private static String URL_PREFIX = "http://localhost:8080";

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void restServiceShouldSaveNewParticipant() {
        RegistrationParticipant participant = new RegistrationParticipant();
        Random random = new Random();
        participant.setFirstName("NICK" + random.nextInt());
        participant.setLastName("HANOT"+ random.nextInt());
        participant.setStamnumber("STAMNUMMER"+ random.nextInt());
        participant.setAdNumber("ADNUMMER"+ random.nextInt());
        participant.setRole(Role.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());

        Adress adress = new Adress();
        adress.setCity("REET"+ random.nextInt());
        adress.setHouseNumber(69+ random.nextInt());
        adress.setPostalCode(6969+ random.nextInt());
        adress.setStreet("REETSESTEENWEG"+ random.nextInt());
        participant.setAdress(adress);
        restTemplate.postForEntity(URL_PREFIX + "/api/participants", participant, ResponseEntity.status(201).getClass());
    }

    @Test
    public void restServiceCanNotSave2SameParticipant() {
        RegistrationParticipant participant = new RegistrationParticipant();
        Random random = new Random();
        participant.setFirstName("NICK"+ random.nextInt());
        participant.setLastName("HANOT"+ random.nextInt());
        participant.setStamnumber("STAMNUMMER"+ random.nextInt());
        participant.setAdNumber("ADNUMMER"+ random.nextInt());
        participant.setRole(Role.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());

        Adress adress = new Adress();
        adress.setCity("REET"+ random.nextInt());
        adress.setHouseNumber(69);
        adress.setPostalCode(6969);
        adress.setStreet("REETSESTEENWEG"+ random.nextInt());
        participant.setAdress(adress);

        ResponseEntity<?> response;
        HttpStatus status;

        response = restTemplate.postForEntity(URL_PREFIX + "/api/participants", participant, ResponseEntity.status(201).getClass());
        status = response.getStatusCode();
        assertEquals(status.value(), 201);
        try {
            restTemplate.postForEntity(URL_PREFIX + "/api/participants", participant, ResponseEntity.badRequest().getClass());
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            //OK
        }
    }
}
