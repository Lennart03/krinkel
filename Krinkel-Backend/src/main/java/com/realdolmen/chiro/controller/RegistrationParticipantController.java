package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationParticipantController {

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    /**
     *
     * Example JSON payload:
     * {
     *    "adNumber": "123456",
     *    "firstName": "Anna-Lyn",
     *    "lastName": "Stardust",
     *    "birthdate": "1991-02-20",
     *    "stamnumber": "AG0104",
     *    "gender": "X",
     *    "function": "MENTOR",
     *    "buddy": false,
     *    "language": [ ],
     *    "eatinghabbit": null,
     *    "remarksFood": null,
     *    "socialPromotion": false,
     *    "medicalRemarks": null,
     *    "remarks": null
     * }
     */
    @RequestMapping(method = RequestMethod.POST, value="/api/participants")
    public ResponseEntity<?> save(@RequestBody RegistrationParticipant participant){
        RegistrationParticipant resultingParticipant = registrationParticipantService.save(participant);
        if(resultingParticipant == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
