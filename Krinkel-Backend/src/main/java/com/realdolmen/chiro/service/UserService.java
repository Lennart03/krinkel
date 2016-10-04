package com.realdolmen.chiro.service;


import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    ChiroUserAdapter userAdapter;


    public User getUser(String username, String passw) {
        System.out.println("useradapter == null?" + (userAdapter == null));
        User u = userAdapter.getUser(username, passw);
        return u;
    }
}
