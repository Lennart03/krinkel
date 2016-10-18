package com.realdolmen.chiro.controller;



import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
*   UserController class
 *  Handles the endpoints for the user resource, which represents a user known to the Chiro.
 */
@RestController
public class UserController {

    @Autowired
    private UserService service;


    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{adNumber}")
    public User getUser(@PathVariable("adNumber") String adNumber) throws UserNotfoundException {
        User u = service.getUser(adNumber);

        if ( u == null )
            throw new UserNotfoundException();

        return u;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class UserNotfoundException extends RuntimeException {
        public UserNotfoundException() {
            super("Gebruiker bestaat niet.");
        }
    }
}
