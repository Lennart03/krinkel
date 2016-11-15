package com.realdolmen.chiro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Contacts the Chiro API to get the stamNumber of a user.
 *
 */
@Service
public class ChiroPloegService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param adNumber for test = 308986
     * @return list of stamNumbers
     * @throws URISyntaxException
     */
    public List<String> getStamNumbers(Integer adNumber) throws URISyntaxException {
        String url =  "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getploeg&json=%7B%22adnr%22:" + adNumber + "%7D";
        List<String> stamNumbers = new ArrayList<>();

        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.getForEntity(uri, String.class)
                .getBody();

        ObjectMapper mapper = new ObjectMapper();

        try{
            JsonNode jsonNode = mapper.readTree(body);
            JsonNode values = jsonNode.get("values");

            values.forEach(v -> stamNumbers.add(v.get("stamnr").asText()));

        } catch (IOException e){
            logger.error("chiro API error");
        }

        return stamNumbers;
    }

    /**
     *
     * @param adNumber for test = 308986
     * @return list of ploegen
     * @throws URISyntaxException
     */
    public List<String> getPloegen(Integer adNumber) throws URISyntaxException {
        String url =  "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getploeg&json=%7B%22adnr%22:" + 308986 + "%7D";
        List<String> ploegen = new ArrayList<>();

        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.getForEntity(uri, String.class)
                .getBody();

        ObjectMapper mapper = new ObjectMapper();

        try{
            JsonNode jsonNode = mapper.readTree(body);
            JsonNode values = jsonNode.get("values");

            values.forEach(v -> ploegen.add(v.get("name").asText()));

        } catch (IOException e){
            logger.error("chiro API error");
        }

        return ploegen;
    }
}
