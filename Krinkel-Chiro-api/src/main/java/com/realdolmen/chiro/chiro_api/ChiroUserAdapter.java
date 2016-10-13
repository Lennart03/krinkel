package com.realdolmen.chiro.chiro_api;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChiroUserAdapter {

    private List<User> users;


    public ChiroUserAdapter() {
        users = new ArrayList<>();
        users.add(new User("386289", "nick.hanot@realdolmen.com", "Nick", "Hanot", "pipi"));
        users.add(new User("386292", "vincent.ceulemans@realdolmen.com", "Vincent", "Ceulemans", "pipi"));
        users.add(new User("386283", "pieter-jan.smets@realdolmen.com", "Pieter-Jan", "Smets", "pipi"));
        users.add(new User("386293", "wannes.vandorpe@realdolmen.com", "Wannes", "Van Dorpe", "pipi"));
        users.add(new User("386288", "mathias.bulte@realdolmen.com", "Mathias", "Bult", "pipi"));
        users.add(new User("169314", "arne.knockaert@realdolmen.com", "Arne", "Knockaert", "pipi"));
        users.add(new User("386290", "philippe.desal@realdolmen.com", "Philippe", "Desal", "pipi"));
        users.add(new User("386291", "timothy.leduc@realdolmen.com", "Timothy", "Leduc", "pipi"));
        users.add(new User("386287", "frank.claes@realdolmen.com", "Frank", "Claes", "pipi"));
        users.add(new User("386318", "ziggy.streulens@realdolmen.com", "Ziggy", "Streulens", "pipi"));
        users.add(new User("386284", "thomas.vanlomberghen@realdolmen.com", "Thomas", "Van Lomberghen", "pipi"));
        users.add(new User("386285", "gil.mathijs@realdolmen.com", "Gil", "Mathijs", "pipi"));
        users.add(new User("386286", "matthias.vanderwilt@realdolmen.com", "Matthias", "Vanderwilt", "pipi"));
    }

    public void syncUser(RegistrationParticipant participant) {
        System.out.print(participant.toString());
    }

    public User getChiroUser(String adNumber) {
        User res = null;

        for (User u : users) {
            if (u.getAdNumber().equals(adNumber))
                res = u;
        }

        return res;
    }
}
