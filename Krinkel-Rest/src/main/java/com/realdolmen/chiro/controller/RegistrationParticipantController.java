package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.controller.validation.EnableRestErrorHandling;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableRestErrorHandling
public class RegistrationParticipantController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    /**
     * Returns HTTP status 201 Created when registration has succeeded.
     * Returns HTTP status 400 Bad Request when:
     *     - A duplicate entry is attempted to be saved
     *     - The JSON payload is malformed.
     *     - The JSON payload contains an incomplete representation (missing fields)
     *          or invalid entries.
     *
     * Example JSON payload:
     {
        "adNumber": "123456",
        "email": "anna-lyn@stardust.org",
        "firstName": "Anna-Lyn",
        "lastName": "Stardust",
        "address": {
             "street": null,
             "houseNumber": 0,
             "postalCode": 0,
             "city": null
         },
        "birthdate": "1991-02-20",
        "stamnumber": "AG0104",
        "gender": "X",
        "role": "LEADER" | "ASPI" | "MENTOR",
        "buddy": false,
        "language": [],
        "eatinghabbit": null,
        "remarksFood": null,
        "socialPromotion": false,
        "medicalRemarks": null,
        "remarks": null
     }
     */
    @RequestMapping(method = RequestMethod.POST, value="/api/participants", consumes = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody RegistrationParticipant participant){
        RegistrationParticipant resultingParticipant = registrationParticipantService.save(participant);
        if(resultingParticipant == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("New registration created.");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
