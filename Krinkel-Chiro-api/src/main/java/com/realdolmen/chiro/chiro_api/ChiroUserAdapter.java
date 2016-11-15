package com.realdolmen.chiro.chiro_api;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

    private List<User> users;


    @PostConstruct
    public void setUp() {
        baseUrl = chiroUrl + "?key=" + key + "&api_key=" + apiKey + "&entity=Light&action=inschrijven&json=%7B%22adnr%22:";
    }


    public ChiroUserAdapter() {
        users = new ArrayList<>();

        // chiro nationaal
        User frank = new User("386287", "frank.claes@realdolmen.com", "Frank", "Claes", "NAT/0000");
        frank.setRole(SecurityRole.ADMIN);
        users.add(frank);

        // chiro leuven
        users.add(new User("386290", "philippe.desal@realdolmen.com", "Philippe", "Desal", "LEG /0000"));

        // chiro demerdal
        users.add(new User("386318", "ziggy.streulens@realdolmen.com", "Ziggy", "Streulens", "LEG/0608"));

        // chiro ourodenberg
        users.add(new User("386289", "nick.hanot@realdolmen.com", "Nick", "Hanot", "LEG/0608"));
        users.add(new User("386292", "vincent.ceulemans@realdolmen.com", "Vincent", "Ceulemans", "LEG/0608"));
        users.add(new User("386283", "pieter-jan.smets@realdolmen.com", "Pieter-Jan", "Smets", "LEG/0608"));
        users.add(new User("386284", "thomas.vanlomberghen@realdolmen.com", "Thomas", "Van Lomberghen", "LEG/0608"));
        users.add(new User("386285", "gil.mathijs@realdolmen.com", "Gil", "Mathijs", "LEG/0608"));
        users.add(new User("386286", "matthias.vanderwilt@realdolmen.com", "Matthias", "Vanderwilt", "LEG/0608"));

        // chiro esjeewee
        User wannes = new User("386293", "wannes.vandorpe@realdolmen.com", "Wannes", "Van Dorpe", "LEG/0607");
        wannes.setRole(SecurityRole.ADMIN);
        users.add(wannes);
        users.add(new User("386288", "mathias.bulte@realdolmen.com", "Mathias", "Bult", "LEG/0607"));
//        users.add(new User("169314", "arne.knockaert@realdolmen.com", "Arne", "Knockaert", "LEG/0607"));
        User arne = new User("169314", "arne.knockaert@realdolmen.com", "Arne", "Knockaert", "LEG/0607");
        arne.setRole(SecurityRole.ADMIN);
        users.add(arne);
        users.add(new User("386291", "timothy.leduc@realdolmen.com", "Timothy", "Leduc", "LEG/0607"));
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

        if (participant instanceof RegistrationVolunteer)
            uriBuilder.append(",%22adnr%22:" + eventCode);

        uriBuilder.append("%7D");

        return new URI(uriBuilder.toString());
    }

    public User getChiroUser(String adNumber) {
        User res = null;

        for (User u : users) {
            if (u.getAdNumber().equals(adNumber))
                res = u;
        }

        return res;
    }

    public List<User> getColleagues(String stamnummer) {
        List<User> res = new ArrayList<>();

        for (User u : users) {
            if (u.getStamnummer().equals(stamnummer))
                res.add(u);
        }

        return res;
    }
}
