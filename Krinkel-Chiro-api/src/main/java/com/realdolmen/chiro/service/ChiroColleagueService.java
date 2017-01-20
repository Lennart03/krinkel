package com.realdolmen.chiro.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realdolmen.chiro.dto.ColleagueDTO;
import com.realdolmen.chiro.dto.ContainerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Contacts the Chiro API to get colleagues of currently logged in user.
 */
@Service
public class ChiroColleagueService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${chiro_url}")
    private String chiroUrl;
    @Value("${chiro_api_key}")
    private String apiKey;
    @Value("${chiro_key}")
    private String chiroKey;

    /**
     * GETs colleagues from the Chiro API
     * <p>
     * Throws exception when the URL isn't valid, checks further above are necessary because of this.
     *
     * @return JSON from Chiro
     */
    public List<ColleagueDTO> getColleagues(Integer adNumber) throws URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        String json = retrieveColleaguesFromChiroApi(adNumber);

        JavaType type = mapper.getTypeFactory().
                constructParametricType(ContainerResponse.class, ColleagueDTO.class);

        ContainerResponse<ColleagueDTO> resp = null;
        try {
            resp = mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp.getValues();
    }

    /**
     * Retrieves a JSON string from the chiro API containing colleagues from the given ad number.
     * Example JSON:
     * {
     * "is_error": 0,
     * "version": 3,
     * "count": 22,
     * "values": [
     * {
     * "adnr": "308986",
     * "first_name": "Hanne",
     * "last_name": "Van Billoen",
     * "afdeling": "L",
     * "functies": [
     * "GG1",
     * "GG2"
     * ],
     * "gender_id": "1",
     * "birth_date": "1983-09-16",
     * "street_address": "Lodistraat 3",
     * "postal_code": "9370",
     * "city": "LEBBEKE",
     * "country": "BE",
     * "phone": "02-0233980",
     * "email": "120845@example.com"
     * }
     * ];
     *
     * @param adNumber The ad number of the user to base the colleagues on.
     * @return Json of colleagues
     * @throws URISyntaxException When the URL is invalid
     */
    private String retrieveColleaguesFromChiroApi(Integer adNumber) throws URISyntaxException {
        String url = chiroUrl +
                "?key=" +
                chiroKey +
                "&api_key=" +
                apiKey +
                "&entity=Light&action=getcollega&json=%7B%22adnr%22:" +
                adNumber +
                "%7D";

        URI uri = new URI(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(uri, String.class).getBody();
    }


    /**
     * Checks if two adNumbers are colleagues.
     *
     * @param ownAdNumber       - current user's adNumber
     * @param colleagueAdNumber adNumber of the user he's subscribing
     * @return
     */
    public boolean isColleague(Integer ownAdNumber, Integer colleagueAdNumber) {
        List<ColleagueDTO> colleagues = null;

        try {
            colleagues = getColleagues(ownAdNumber);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (colleagues == null) {
            return false;
        }

        return colleagues.stream().anyMatch(c -> c.getAdNumber().equals(colleagueAdNumber.toString()));
    }
}
