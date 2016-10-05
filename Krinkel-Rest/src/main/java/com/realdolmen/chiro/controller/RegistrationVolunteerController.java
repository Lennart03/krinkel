package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.controller.validation.EnableRestErrorHandling;
import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.service.RegistrationVolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@RestController
@EnableRestErrorHandling
public class RegistrationVolunteerController {

    @Autowired
    private RegistrationVolunteerService registrationVolunteerService;

    /**
     * TODO: More detail in docs
     *
     * Example JSON payload. (Pipes denote that the value is one from the list)
     {
         "adNumber": "123456789",
         "firstName": "Aster",
         "lastName": "Deckers",
         "address": {
             "street": "Melkweg",
             "houseNumber": 123,
             "postalCode": 1850,
             "city": "Alderaan Prime"
         },
         "birthdate": "1995-05-01",
         "stamnumber": "AG0001",
         "gender": "MAN" | "WOMAN" | "X",
         "role": "LEADER" | "ASPI" | "MENTOR",
         "buddy": true | false,
         "language": [
                "ENGLISH",
                "SPANISH"
         ],
         "eatinghabbit": "VEGI" | "HALAL" | FISHANDMEAT
         "remarksFood": null,
         "socialPromotion": true | false,
         "medicalRemarks": null,
         "remarks": null,
         "phoneNumber": null,
         "campGround": "ANTWERPEN" | ... ,
         "function": {
         "preset": "KLINKER_EDITORIAL" | ...,
         "other": null
         },
         "preCampList": [
                 {
                     "id": 10,
                     "date": "2017-08-21"
                 }
                 // , ...
          ],
         "postCampList": [ ]
     }

     Pre and Post Camp -> see data.sql for available options.
     */
    @RequestMapping(method = RequestMethod.POST, value="/api/volunteers", consumes = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody RegistrationVolunteer volunteer){
        RegistrationVolunteer resultingVolunteer = registrationVolunteerService.save(volunteer);
        if(resultingVolunteer == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value="/api/volunteers", produces = "application/json")
    public @ResponseBody RegistrationVolunteer retrieve(){

        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        RegistrationVolunteer volunteer = new RegistrationVolunteer(
                "123456789", "Aster", "Deckers", c.getTime(),
                "AG0001", Gender.MAN, Role.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KLINKER_EDITORIAL)
        );

        PreCamp preCamp = new PreCamp();
        preCamp.setId(1);
        preCamp.setDate(new Date());
        volunteer.addPreCamp(preCamp);

        volunteer.setLanguage(
                Arrays.asList(
                        Language.ENGLISH,
                        Language.SPANISH
                )
        );

        volunteer.setAddress(new Address("-","-",1500,"-"));
        return volunteer;
    }
}
