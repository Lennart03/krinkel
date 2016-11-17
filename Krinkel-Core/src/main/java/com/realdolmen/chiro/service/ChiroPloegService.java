package com.realdolmen.chiro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realdolmen.chiro.domain.SecurityRole;
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
     * @return list of ploegen
     * @throws URISyntaxException
     */
    public List<String> getPloegen(Integer adNumber) throws URISyntaxException {
        //TODO use adNumber variable and not hardcoded 308986
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

            for(JsonNode v: values){
                ploegen.add(v.get("stamnr").asText()+": "+v.get("name").asText());
            }

        } catch (IOException e){
            logger.error("chiro API error");
        }

        return ploegen;
    }

    public List<String> getStamNumbers(String adNumber) {
        String url =  "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getploeg&json=%7B%22adnr%22:" + 308986 + "%7D";
        List<String> stamNumbers = new ArrayList<>();
        try{
            URI uri = new URI(url);

            RestTemplate restTemplate = new RestTemplate();
            String body = restTemplate.getForEntity(uri, String.class)
                    .getBody();

            ObjectMapper mapper = new ObjectMapper();

            try{
                JsonNode jsonNode = mapper.readTree(body);
                JsonNode values = jsonNode.get("values");

                for(JsonNode v: values){
                    stamNumbers.add(v.get("stamnr").asText());
                }

            } catch (IOException e){
                logger.error("chiro API error");
            }
        }catch (URISyntaxException e){
            logger.error(e.getMessage());
        }
        return stamNumbers;
    }

}
