package com.realdolmen.chiro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
/**
 *
 * Contacts the Chiro API to get colleagues of currently logged in user.
 *
 */
@Service
public class ChiroColleagueService {


    @Value("${chiro_url}")
    private String chiroUrl;

    @Value("${chiro_api_key}")
    private String apiKey;

    @Value("${chiro_key}")
    private String chiroKey;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * GETs colleagues from the Chiro API
     *
     * Throws exception when the URL isn't valid, checks further above are necessary because of this.
     * @return JSON from Chiro
     */
    public String getColleagues(Integer adNumber) throws URISyntaxException {
        /**
         * Example URL
         * "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getcollega&json=%7B%22adnr%22:" + adNumber + "%7D";
         */
        //TODO use adNumber variable and not hardcoded 308986
        String url = chiroUrl + "?key=" + chiroKey + "&api_key=" + apiKey + "&entity=Light&action=getcollega&json=%7B%22adnr%22:" + adNumber + "%7D";

        /**
         * Throws exception when the URL isn't valid, no further checks necessary because of this.
         */
        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(uri, String.class).getBody();
    }


    /**
     * Checks if two adNumbers are colleagues.
     * @param ownAdNumber - current user's adNumber
     * @param colleagueAdNumber adNumber of the user he's subscribing
     * @return
     */
    public Boolean isColleague(Integer ownAdNumber, Integer colleagueAdNumber) {
        String colleagues = null;
        Boolean isColleague = false;

        try {
             colleagues = getColleagues(ownAdNumber);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (colleagues == null) {
            return false;
        }
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = null;


        try {
            jsonNode = mapper.readTree(colleagues);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode values = jsonNode.get("values");


        for (JsonNode value : values) {
            if (value.get("adnr").asInt() == colleagueAdNumber) {
                isColleague = true;
                break;
            }
        }

        return isColleague;
    }
}
