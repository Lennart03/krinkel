package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private ChiroUserAdapter adapter;

    public User getUser(String adNumber) {
        User u = adapter.getChiroUser(adNumber);

        if (u != null) {
            u.setAdNumber(adNumber);
            u.setHasPaid(false);
            u.setRegistered(false);
        }

        return u;
    }
}
