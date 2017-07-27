package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.payments.TicketPrice;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.payment.TicketDTO;
import com.realdolmen.chiro.service.TicketService;
import com.realdolmen.chiro.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private UserService userService;
    private TicketService ticketService;

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    public TicketController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/purchase", consumes = "application/json")
    public ResponseEntity<?> orderTicket(@RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        logger.info("Ordening new tickets: [" + ticketDTO.toString() + "]");
        String paymentUrl = ticketService.createPayment(ticketDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(paymentUrl));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Returns the address of the current user.
     * If no user is found an {@link HttpStatus#NOT_FOUND} is returned as status
     *
     * @return {@link ResponseEntity} with the address of the current logged in user.
     */
    @RequestMapping(value = "/address", produces = "application/json")
    public ResponseEntity<?> getUserAddress() {
        User currentUser = userService.getCurrentUser();
        if (invalidUser(currentUser)) {
            logger.error("No user found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            RegistrationParticipant participant = userService.getRegistrationParticipant(currentUser.getAdNumber());
            if (participant.getAddress() == null) {
                logger.error("No address for current user");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                logger.info("User");
                return new ResponseEntity<>(participant, HttpStatus.OK);
            }
        }
    }

    @RequestMapping("/prices/train")
    public List<TicketPrice> getTrainTicketPrice() {
        return ticketService.getPricesForTickets(TicketType.TREIN);
    }

    @RequestMapping("/prices/coupons")
    public List<TicketPrice> getCouponPrices() {
        return ticketService.getPricesForTickets(TicketType.BON);
    }

    /**
     * Checks is the given user is valid.
     * The user is valid if he is registered and has an ad number.
     *
     * @param user User to check
     * @return If the user is invalid or not
     */
    private boolean invalidUser(User user) {
        return user == null || !user.getIsRegistered() || user.getAdNumber() == null || user.getAdNumber().length() == 0;
    }

}
