package com.realdolmen.chiro.controller;



import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
*   UserController class
 *  Handles the endpoints for the user resource, which represents a user known to the Chiro.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Security has to stay here instead of userService.getUser(...) since cas also uses that method.
     * If you want secure it in the service I wish you the best of luck.
     *
     * @param adNumber
     * @return
     * @throws UserNotfoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{adNumber}")
    @PreAuthorize("@UserControllerSecurity.hasPermissionToGetUser(#adNumber)")
    public User getUser(@PathVariable("adNumber") String adNumber) throws UserNotfoundException {
//        User u = userService.getUser(adNumber);

        User u = userService.getCurrentUser();
        if ( u == null ) {
            throw new UserNotfoundException();
        }

        return u;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/user")
    public User getUser() throws UserNotfoundException {
//        User u = userService.getUser(adNumber);

        User u = userService.getCurrentUser();
        if ( u == null ) {
            throw new UserNotfoundException();
        }

        System.out.println(u.getEmail());
        return u;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class UserNotfoundException extends RuntimeException {
        public UserNotfoundException() {
            super("Gebruiker bestaat niet.");
        }
    }
}
