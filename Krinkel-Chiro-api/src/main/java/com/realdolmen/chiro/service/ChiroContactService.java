package com.realdolmen.chiro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realdolmen.chiro.converters.ChiroContactMapper;
import com.realdolmen.chiro.containers.ChiroContactContainer;
import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.domain.units.ChiroContact;
import com.realdolmen.chiro.domain.units.HttpChiroContact;
import com.realdolmen.chiro.exception.NoContactFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;


/**
 *
 * Contacts the Chiro API to get user info
 *
 */
@Service
public class ChiroContactService {
    @Value("${chiro_url}")
    private String chiroUrl;

    @Value("${chiro_api_key}")
    private String apiKey;

    @Value("${chiro_key}")
    private String chiroKey;


    /**
     * GET JSON from Chiro, no need to map this to an object because it's only used in the frontend.
     * adNmber for test = 308986
     * @return JSON from Chiro
     */
    public String getContact(Integer adNumber) throws URISyntaxException {
        /**
         * Example URL
         * "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getcontact&json=%7B%22adnr%22:" + adNumber + "%7D";
         */

        String url = chiroUrl + "?key=" + chiroKey + "&api_key=" + apiKey + "&entity=Light&action=getcontact&json=%7B%22adnr%22:" + adNumber + "%7D";
        //String url = chiroUrl + "?key=" + chiroKey + "&api_key=" + apiKey + "&entity=Light&action=getcontact&json=%7B%22adnr%22:" + adNumber + "%7D";


        // Throws exception when the URL isn't valid, no further checks necessary because of this.
        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(uri, String.class)
                           .getBody();
    }

    private ChiroContact getDummyParticipant() {
        ChiroContact chiroContact = new ChiroContact();
        chiroContact.setAdnr("1");
        chiroContact.setEmail("fake@nomail.com");
        chiroContact.setFirstName("not found");
        chiroContact.setLastName("not found");
        chiroContact.setPostalCode(null);
        chiroContact.setBirthDate("");
        chiroContact.setAfdeling(null);
        chiroContact.setFuncties(null);
        chiroContact.setCity(null);
        chiroContact.setCountry(null);
        chiroContact.setGender(Gender.MAN);
        chiroContact.setStreetAddress(null);
        chiroContact.setPhone(null);
        chiroContact.setId(null);
        return chiroContact;
    }

    public ChiroContact getChiroContact(Integer adNummer) throws URISyntaxException, IOException {
        String json = getContact(adNummer);
        ObjectMapper objectMapper = new ObjectMapper();
        ChiroContactMapper chiroContactMapper = new ChiroContactMapper();
        ChiroContactContainer chiroContactsContainer = objectMapper.readValue(json, ChiroContactContainer.class);
        if(Integer.parseInt(chiroContactsContainer.getCount()) < 1) {
            return new HttpChiroContact(getDummyParticipant(), "404");
        } else {
            return new HttpChiroContact(chiroContactMapper.retrieveChiroContact(chiroContactsContainer), "200");
        }
    }
}
