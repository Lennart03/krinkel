package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.ConfirmationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/confirmation")
public class ConfirmationLinkController {

    @Autowired
    private ConfirmationLinkService service;

    @RequestMapping(method = RequestMethod.GET)
    public String confirm(@RequestParam("ad") String adNumber, @RequestParam("token") String token, Model model){
        boolean success = service.checkToken(adNumber, token);

        model.addAttribute("success", success);
/*
        if(success){
            return "{\"success\" : true}";
        }
        else{
            return "{\"success\" : false}";
        }*/
        return "confirmation";
    }
}
