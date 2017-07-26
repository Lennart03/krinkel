package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private UserService userService;

    @Autowired
    public TicketController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/purchase")
    public void orderTicket() {

    }

    /**
     * Returns the address of the current user.
     * If no user is found an {@link HttpStatus#NOT_FOUND} is returned as status
     * @return {@link ResponseEntity} with the address of the current logged in user.
     */
    @RequestMapping(value = "/address", produces = "application/json")
    public ResponseEntity<?> getUserAddress() {
        User currentUser = userService.getCurrentUser();
        if(invalidUser(currentUser)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Address address = userService.getRegistrationParticipant(currentUser.getAdNumber()).getAddress();
            if(address == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(address, HttpStatus.OK);
            }
        }
    }

    /**
     * Checks is the given user is valid.
     * The user is valid if he is registered and has an ad number.
     * @param user User to check
     * @return If the user is invalid or not
     */
    private boolean invalidUser(User user) {
        return user == null || !user.getIsRegistered() || user.getAdNumber() == null || user.getAdNumber().length() == 0;
    }

}
