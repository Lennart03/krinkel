package com.realdolmen.chiro.chiro_api;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ChiroUserAdapter {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ChiroUserAdapter.class);


    @Value("${chiro_url}")
    private String chiroUrl;

    @Value("${chiro_api_key}")
    private String apiKey;

    @Value("${chiro_key}")
    private String key;


    @Value("${chiro_event_code}")
    private String eventCode;

    private String baseUrl;

    @PostConstruct
    public void setUp() {
        baseUrl = chiroUrl + "?key=" + key + "&api_key=" + apiKey + "&entity=Light&action=inschrijven";
    }


    public ChiroUserAdapter() {

    }

    public void syncUser(RegistrationParticipant participant) throws URISyntaxException {
        // Throws exception when the URL isn't valid, no further checks necessary because of this.
        URI uri = constructRequestUri(participant);
        logger.info("Performing sync call with URI: " + uri.toString());

        // Perform call
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForEntity(uri, String.class)
                .getBody();
    }

    private URI constructRequestUri(RegistrationParticipant participant) throws URISyntaxException {
        StringBuilder uriBuilder = new StringBuilder(baseUrl + "&json=%7B%22adnr%22:" + participant.getAdNumber());
        uriBuilder.append(",%22event_id%22:" + eventCode);

        if (participant instanceof RegistrationVolunteer)
            uriBuilder.append(",%22role_id%22:2");

        uriBuilder.append("%7D");

        return new URI(uriBuilder.toString());
    }
}
