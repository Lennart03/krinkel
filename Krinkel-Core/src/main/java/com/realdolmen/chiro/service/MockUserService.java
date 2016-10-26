package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class MockUserService extends UserService {


    @Override
    public User getCurrentUser() {
        User user = new User();
        user.setAdNumber("123456");
        return user;

    }
}
