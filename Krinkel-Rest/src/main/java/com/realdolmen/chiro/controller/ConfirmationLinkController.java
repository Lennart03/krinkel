package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ConfirmationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/confirmation")
public class ConfirmationLinkController {

    @Autowired
    private ConfirmationLinkService service;

    @RequestMapping(method = RequestMethod.GET)
    public void confirm(@RequestParam("ad") String adNumber, @RequestParam("token") String token){
        service.checkToken(adNumber, token);
    }
}
