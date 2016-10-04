package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public User getUser(@RequestParam("user") String user, @RequestParam("password") String passw) {
        return service.getUser(user, passw);
    }
}
