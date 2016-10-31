package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ChiroContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

@RestController
public class ChiroContactController {

    @Autowired
    private ChiroContactService chiroContactService;


    @RequestMapping("/api/contact/{adNumber}")
    public String getContact(@PathVariable Integer adNumber) {
        try {
            //TODO CHANGE THE NEXT LINE, THIS IS DUMMY! CHIRO STUFF DOESN'T WORK YET, NO RESULTS OTHERWISE
            return chiroContactService.getContact(308986);
//            return chiroContactService.getContact(adNumber);
        } catch (URISyntaxException e) {
            throw new InvalidAdNumber();
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class InvalidAdNumber extends RuntimeException {
        public InvalidAdNumber() {
            super("Ongeldig adnummer.");
        }
    }


}
