package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class RegistrationParticipantController {

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @RequestMapping(method = RequestMethod.POST, value="/api/participants")
    public ResponseEntity<?> save(@RequestBody RegistrationParticipant participant){
        RegistrationParticipant resultingParticipant = registrationParticipantService.save(participant);
        if(resultingParticipant == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
