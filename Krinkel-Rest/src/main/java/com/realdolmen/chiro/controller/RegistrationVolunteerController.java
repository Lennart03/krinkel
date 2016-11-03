package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.controller.validation.EnableRestErrorHandling;
import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.service.RegistrationVolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@RestController
@EnableRestErrorHandling
public class RegistrationVolunteerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegistrationVolunteerService registrationVolunteerService;

    @Autowired
    private MultiSafePayService mspService;

    /**
     * Returns HTTP status 201 Created when registration has succeeded.
     * Returns HTTP status 400 Bad Request when:
     * - A duplicate entry is attempted to be saved
     * - The JSON payload is malformed.
     * - The JSON payload contains an incomplete representation (missing fields)
     * or invalid entries.
     * <p>
     * Example JSON payload. (Pipes denote that the value is one from the list)
     * {
     * "adNumber": "123456789",
     * "email" : "example@acme.com",
     * "firstName": "Aster",
     * "lastName": "Deckers",
     * "address": {
     * "street": "Melkweg",
     * "houseNumber": 123,
     * "postalCode": 1850,
     * "city": "Alderaan Prime"
     * },
     * "birthdate": "1995-05-01",
     * "stamnumber": "AG0001",
     * "gender": "MAN" | "WOMAN" | "X",
     * "role": "LEADER" | "ASPI" | "MENTOR",
     * "buddy": true | false,
     * "language": [
     * "ENGLISH",
     * "SPANISH"
     * ],
     * "eatinghabbit": "VEGI" | "HALAL" | FISHANDMEAT
     * "remarksFood": null,
     * "socialPromotion": true | false,
     * "medicalRemarks": null,
     * "remarks": null,
     * "phoneNumber": null,
     * "campGround": "ANTWERPEN" | ... ,
     * "function": {
     * "preset": "KRINKEL_EDITORIAL" | ...,
     * "other": null
     * },
     * "preCampList": [
     * {
     * "id": 10,
     * "date": "2017-08-21"
     * }
     * // , ...
     * ],
     * "postCampList": [ ]
     * }
     * <p>
     * Pre and Post Camp -> see data.sql for available options.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/api/volunteers", consumes = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody RegistrationVolunteer volunteer) throws URISyntaxException, MultiSafePayService.InvalidPaymentOrderIdException {
        RegistrationVolunteer resultingVolunteer = registrationVolunteerService.save(volunteer);
        if (resultingVolunteer == null) {
            logger.info("Registration for Volunteer failed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        Integer price = registrationVolunteerService.PRICE_IN_EUROCENTS;
        String paymentUrl = mspService.getVolunteerPaymentUri(resultingVolunteer, price);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(paymentUrl));
        logger.info("New Registration for Volunteer created.");

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/volunteers", produces = "application/json")
    public
    @ResponseBody
    RegistrationVolunteer retrieve() {

        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        RegistrationVolunteer volunteer = new RegistrationVolunteer(
                "123456789", "example@acme.com", "Aster", "Deckers", c.getTime(),
                "AG0001", Gender.MAN, EventRole.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KRINKEL_EDITORIAL), "aster.deckers@example.org"
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

        volunteer.setAddress(new Address("-", "-", 1500, "-"));
        return volunteer;
    }
}
