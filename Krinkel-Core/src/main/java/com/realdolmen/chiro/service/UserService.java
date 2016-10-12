package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.User;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    public User getUser(String adNumber) {
        User u = new User();
        u.setAdNumber(adNumber);
        u.setHasPaid(true);
        u.setIsRegistered(true);
        return u;
    }
}
