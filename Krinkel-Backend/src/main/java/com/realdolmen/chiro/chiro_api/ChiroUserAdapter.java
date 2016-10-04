package com.realdolmen.chiro.chiro_api;

import com.realdolmen.chiro.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ChiroUserAdapter {

    public ArrayList<User> users = new ArrayList();

    public ChiroUserAdapter() {
        users.add(new User("Ziggy", "test", "user", "ad1", "abcdefg", false, "AG0103"));
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
