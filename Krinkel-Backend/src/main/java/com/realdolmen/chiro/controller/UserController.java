package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public User getUser(@RequestParam("user") String user, @RequestParam("password") String passw) {
        User u = service.getUser(user, passw);
        this.validateUser(u);
        return u;
    }

    private void validateUser(User u) {
        if(u == null){
            throw new UserNotfoundException();
        }
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private class UserNotfoundException extends RuntimeException {
        public UserNotfoundException() {
            super("Ongeldige gebruikersgegevens");
        }
    }
}
