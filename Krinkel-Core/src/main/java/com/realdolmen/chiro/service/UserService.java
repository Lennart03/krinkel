package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private ChiroUserAdapter adapter;

    @Autowired
    private RegistrationParticipantRepository repo;

    public User getUser(String adNumber) {
        User u = adapter.getChiroUser(adNumber);

        if (u != null) {
            RegistrationParticipant participant = repo.findByAdNumber(u.getAdNumber());

            if (participant != null) {
                u.setRegistered(true);

                if (participant.getStatus() == Status.PAID)
                    u.setHasPaid(true);
            }

        }

        return u;
    }
}
