package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ChiroColleagueService;
import com.realdolmen.chiro.service.ChiroContactService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ChiroColleagueController {

    @Autowired
    private ChiroColleagueService chiroColleagueService;

    @Autowired
    private UserService userService;


    @RequestMapping("/api/colleagues")
    public List<String> getColleagues() {
        try {
            //TODO CHANGE THE NEXT LINE, THIS IS DUMMY! CHIRO STUFF DOESN'T WORK YET, NO RESULTS OTHERWISE
            return chiroColleagueService.getColleagues(221826);
//            return chiroColleagueService.getColleagues(
//                    Integer.parseInt(
//                            userService.getCurrentUser().getAdNumber()));
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
