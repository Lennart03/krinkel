package com.realdolmen.chiro.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Contacts the Chiro API to get colleagues of currently logged in user.
 *
 */
@Service
public class ChiroColleagueService {

    @Autowired
    private UserService userService;

    /**
     * GET JSON from Chiro, no need to map this to an object because it's only used in the frontend.
     * adNmber for test = 308986
     * @return JSON from Chiro
     */
    public List<String> getColleagues(Integer adNumber) throws URISyntaxException {
        List<String> listOfAvailableColleagues = new ArrayList<>();

        String url = "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getcollega&json=%7B%22adnr%22:" + adNumber + "%7D";


        /**
         * Throws exception when the URL isn't valid, no further checks necessary because of this.
         */
        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();

        String body = restTemplate.getForEntity(uri,
                String.class).getBody();


        ObjectMapper mapper = new ObjectMapper();


        try {
            JsonNode jsonNode = mapper.readTree(body);
            JsonNode values = jsonNode.get("values");

            values.forEach(v -> {
                RegistrationParticipant participant = userService.getRegistrationParticipant(v.get("adnr").asText());

                if (participant != null) {
                    if (participant.getStatus() == Status.PAID) {
                        return;
                    }
                }

                listOfAvailableColleagues.add(v.toString());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfAvailableColleagues;
    }

}
