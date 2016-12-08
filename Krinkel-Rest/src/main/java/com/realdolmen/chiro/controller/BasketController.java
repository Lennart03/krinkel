package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
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

}
