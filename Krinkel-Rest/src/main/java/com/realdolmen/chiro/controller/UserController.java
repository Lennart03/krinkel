package com.realdolmen.chiro.controller;


import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * UserController class
 * Handles the endpoints for the user resource, which represents a user known to the Chiro.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get the current loggedIn user, no params needed since we cache the user in the userService
     * @return
     * @throws UserNotfoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/user")
    public User getUser() throws UserNotfoundException {
        User u = userService.getCurrentUser();
        if (u == null) {
            throw new UserNotfoundException();
        }
        return u;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class UserNotfoundException extends RuntimeException {
        public UserNotfoundException() {
            super("Gebruiker bestaat niet.");
        }
    }
}
