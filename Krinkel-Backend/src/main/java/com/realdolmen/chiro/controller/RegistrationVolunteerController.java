package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.service.RegistrationVolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class RegistrationVolunteerController {

    @Autowired
    private RegistrationVolunteerService registrationVolunteerService;

    /**
     * TODO: More detail in docs
     *
     * Example JSON payload:
     {
         "adNumber": "123456789",
         "firstName": "Aster",
         "lastName": "Deckers",
         "adress": {
             "street": null,
             "houseNumber": 0,
             "postalCode": 0,
             "city": null
         },
         "birthdate": 1475659258424,
         "stamnumber": "AG0001",
         "gender": "MAN",
         "role": "LEADER",
         "buddy": false,
         "language": [ ],
         "eatinghabbit": "VEGI",
         "remarksFood": null,
         "socialPromotion": false,
         "medicalRemarks": null,
         "remarks": null,
         "phoneNumber": null,
         "campGround": "ANTWERPEN",
         "function": {
         "preset": "KLINKER_EDITORIAL",
         "other": null
         },
         "preCampList": [ ],
         "postCampList": [ ]
     }
     */
    @RequestMapping(method = RequestMethod.POST, value="/api/volunteers", consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody RegistrationVolunteer volunteer){
        RegistrationVolunteer resultingVolunteer = registrationVolunteerService.save(volunteer);
        if(resultingVolunteer == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value="/api/volunteers", produces = "application/json")
    public @ResponseBody RegistrationVolunteer retrieve(){
        RegistrationVolunteer volunteer = new RegistrationVolunteer(
                "123456789", "Aster", "Deckers", new Date(),
                "AG0001", Gender.MAN, Role.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KLINKER_EDITORIAL)
        );
        volunteer.setAdress(new Adress());
        return volunteer;
    }
}
