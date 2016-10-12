package com.realdolmen.chiro.chiro_api;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ChiroUserAdapter {

    public void syncUser(RegistrationParticipant participant) {
        System.out.print(participant.toString());
    }
}
