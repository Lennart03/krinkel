package com.realdolmen.chiro.chiro_api;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChiroUserAdapter {

    private List<User> users;


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
        users.add(new User("169314", "arne.knockaert@realdolmen.com", "Arne", "Knockaert", "LEG/0607"));
        users.add(new User("386291", "timothy.leduc@realdolmen.com", "Timothy", "Leduc", "LEG/0607"));
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

    public List<User> getColleagues(String stamnummer) {
        List<User> res = new ArrayList<>();

        for (User u : users) {
            if (u.getStamnummer().equals(stamnummer))
                res.add(u);
        }

        return res;
    }
}
