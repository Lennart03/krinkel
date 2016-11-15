package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ChiroPloegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ChiroPloegController {
    @Autowired
    private ChiroPloegService chiroPloegService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/ploegen/{adNumber}")
    public List<String> getPloegen(@PathVariable Integer adNumber) {
        try {
            return chiroPloegService.getPloegen(adNumber);
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
