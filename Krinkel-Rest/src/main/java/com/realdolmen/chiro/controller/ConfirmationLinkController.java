package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ConfirmationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/confirmation", produces = "application/json")
public class ConfirmationLinkController {

    @Autowired
    private ConfirmationLinkService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String confirm(@RequestParam("ad") String adNumber, @RequestParam("token") String token){
        boolean success = service.checkToken(adNumber, token);

        if(success){
            return "{\"success\" : true}";
        }
        else{
            return "{\"success\" : false}";
        }
    }
}
