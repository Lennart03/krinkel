package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.exception.NoContactFoundException;
import com.realdolmen.chiro.service.ChiroContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * used to prefill the form
     * @param adNumber
     * @return
     */
    @RequestMapping("/api/contact/{adNumber}")
    public String getContact(@PathVariable Integer adNumber) {
        try {
            return chiroContactService.getContact(adNumber);
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

    @ResponseStatus(value=HttpStatus.NOT_FOUND,reason="Er bestaat geen persoon met deze adNummer")
    public class NoContactFoundExcep extends RuntimeException {
        private static final long serialVersionUID = -3712981290802922344L;
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public NoContactFoundExcep(String message) {
            super("Er bestaat geen persoon met deze adNummer");
            this.message=message;
            System.out.println("Controller class throw error");
        }
    }
/*
    @ExceptionHandler(NoContactFoundExcep.class)
      public ModelAndView handleNoContactFoundExcep(NoContactFoundExcep ex)
    {
        System.out.println("Called exception handler");
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception",ex);
        return model;
    }
*/
    @RequestMapping("/api/participant/{adNumber}")
    public@ResponseBody RegistrationParticipant getParticipant(@PathVariable Integer adNumber) {
        try {
            return chiroContactService.getRegistrationParticipant(adNumber);
        } catch (URISyntaxException e) {
            throw new InvalidAdNumber();
        } catch( NoContactFoundException e){
            throw new NoContactFoundExcep(e.getMessage());
        }
    }

}
