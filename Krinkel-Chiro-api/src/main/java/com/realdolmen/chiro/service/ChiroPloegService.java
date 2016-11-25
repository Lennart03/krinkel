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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Contacts the Chiro API to get the stamNumber of a user.
 */
@Service
public class ChiroPloegService {


    @Value("${chiro_url}")
    private String chiroUrl;

    @Value("${chiro_api_key}")
    private String apiKey;

    @Value("${chiro_key}")
    private String chiroKey;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Returns a List<String> like this: stamNumber: name
     *
     * @param adNumber for test = 308986
     * @return list of ploegen
     * @throws URISyntaxException EXAMPLE URL
     *                            https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key
     *                            =vooneih8oo1XepeiduGh&entity=Light&action=getploeg&json=%7B%22adnr%22:" + 308986 + "%7D"
     */
    public List<String> getPloegen(Integer adNumber) throws URISyntaxException {
        //TODO use adNumber variable and not hardcoded 308986
        String url = chiroUrl + "?key=" + chiroKey + "&api_key=" + apiKey + "&entity=Light&action=getploeg&json=%7B%22adnr%22:" + 308986 + "%7D";

        List<String> ploegen = new ArrayList<>();

        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.getForEntity(uri, String.class)
                .getBody();

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonNode = mapper.readTree(body);
            JsonNode values = jsonNode.get("values");

            for (JsonNode v : values) {
                ploegen.add(v.get("stamnr").asText() + ": " + v.get("name").asText());
            }

        } catch (IOException e) {
            logger.error("chiro API error");
        }

        return ploegen;
    }

    public Map<String, String> getStamNumbers(String adNumber) {
        String url = "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getploeg&json=%7B%22adnr%22:" + 308986 + "%7D";
        Map<String, String> stamNumbers = new TreeMap<>();
//        try {
//            URI uri = new URI(url);
//
//            RestTemplate restTemplate = new RestTemplate();
//            String body = restTemplate.getForEntity(uri, String.class)
//                    .getBody();
//
//            ObjectMapper mapper = new ObjectMapper();
//
//            try {
//                JsonNode jsonNode = mapper.readTree(body);
//                JsonNode values = jsonNode.get("values");
//
//                for (JsonNode v : values) {
//                    String stamNumber = v.get("stamnr").asText();
//                    String upperStamNumber = v.get("bovenliggende").asText();
//
//                    stamNumbers.put(stamNumber, upperStamNumber);
//                }
//
//            } catch (IOException e) {
//                logger.error("chiro API error");
//            }
//        } catch (URISyntaxException e) {
//            logger.error(e.getMessage());
//        }
        stamNumbers.put("MG /0113","MG0100");
//        stamNumbers.put("AG /0103","AG0400"); //groep
//        stamNumbers.put("KG /0000", "KG0000"); //verbond
        stamNumbers.put("AG /0400", "AG0000"); //gewest
//        stamNumbers.put("LG /0000","LG0000");
//        stamNumbers.put("NAT /0000", "00");
        return stamNumbers;
    }

}
