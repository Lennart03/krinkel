package com.realdolmen.chiro.service;


import com.realdolmen.chiro.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    public ArrayList<User> users = new ArrayList();

    public UserService() {
        users.add(new User("Ziggy", "test", "user", "ad1", "abcdefg", true, "AG0103"));
        users.add(new User("PJ", "password", "admin", "ad2", "hijklmnop", false, "LEG0600"));
    }

    public User getUser(String username, String passw) {
        User res = null;
        for (User u : this.users) {
            if (username.equals(u.getUsername()))
                if (passw.equals(u.getPassword()))
                    res = u;
        }
        return res;
    }
}
