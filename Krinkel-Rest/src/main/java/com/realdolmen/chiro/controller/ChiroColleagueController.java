package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.dto.ColleagueDTO;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ChiroColleagueController {

    @Autowired
    private UserService userService;

    /**
     * finding colleagues to enroll
     *
     * @return
     */
    @RequestMapping("/api/colleagues")
    public List<ColleagueDTO> getAvailableColleagues() {
        try {
            return userService.getAvailableColleagues(Integer.parseInt(userService.getCurrentUser().getAdNumber()));
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
