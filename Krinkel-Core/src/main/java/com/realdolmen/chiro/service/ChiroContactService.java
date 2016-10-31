package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Contacts the Chiro API to get user info
 *
 */
@Service
public class ChiroContactService {


    /**
     * GET JSON from Chiro, no need to map this to an object because it's only used in the frontend.
     * adNmber for test = 308986
     * @return JSON from Chiro
     */
    public String getContact(Integer adNumber) throws URISyntaxException {
        String url = "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getcontact&json=%7B%22adnr%22:" + adNumber + "%7D";


        /**
         * Throws exception when the URL isn't valid, no further checks necessary because of this.
         */
        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(uri,
                    String.class).getBody();


    }

}
