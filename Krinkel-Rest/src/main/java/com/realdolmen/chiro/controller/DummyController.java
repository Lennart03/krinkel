package com.realdolmen.chiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @RequestMapping("/dummy")
    public String dummy(){
        return "Hello World";
    }

}
