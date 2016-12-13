package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.service.BasketService;
import com.realdolmen.chiro.service.dto.EmailHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;

    // api/basket - GET --> list of users in basket
    // api/basket - POST/PUT --> add user to basket

    @RequestMapping(value = "/api/basket", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<RegistrationParticipant> getUsersInBasket() {
        return basketService.getUsersInBasket();
    }

    @RequestMapping(value = "/api/basket", consumes = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity addUser(@Valid @RequestBody RegistrationParticipant user) {
        basketService.addUserToBasket(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/basket/mail", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getMail() {
        return basketService.getRegistrationEmail();
    }

    @RequestMapping(value = "/api/basket/mail", consumes = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.PUT, RequestMethod.POST})
    public void setMail(@Valid @RequestBody EmailHolder mail) {
        basketService.setRegistrationEmail(mail.getEmail());
    }


    @RequestMapping(value = "/api/basket/pay", method = RequestMethod.GET)
    public ResponseEntity<?> initializePayment() throws Exception {
        String paymentUrl = basketService.initializePayment();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(paymentUrl));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //todo: When using the DELETE method spring will return "invalid cors request". Find out why and fix!
    @RequestMapping(value = "/api/basket/delete/{adNumber}", method = RequestMethod.GET)
    public ResponseEntity removeUser(@PathVariable String adNumber) {
        Optional<RegistrationParticipant> user = basketService.getUsersInBasket().stream().filter(u -> u.getAdNumber().equals(adNumber)).findFirst();

        if (user.isPresent()) {
            basketService.removeUserFromBasket(user.get());
            return new ResponseEntity((HttpStatus.OK));
        }
        return new ResponseEntity((HttpStatus.BAD_REQUEST));
    }

}
