package com.realdolmen.chiro.service;


import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    ChiroUserAdapter userAdapter;

    @Autowired
    RegistrationParticipantRepository repository;


    public User getUser(String username, String passw) {
        System.out.println("useradapter == null?" + (userAdapter == null));
        User u = userAdapter.getUser(username, passw);
        if (u != null) {
            Boolean isRegistered = repository.findByAdNumber(u.getAdNumber()) != null;
            u.setSubscribed(isRegistered);
        }
        return u;
    }
}
