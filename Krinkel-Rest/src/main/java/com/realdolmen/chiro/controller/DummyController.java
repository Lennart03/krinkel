package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.config.AuthRole;
import com.realdolmen.chiro.domain.Role;
import com.sun.media.sound.SoftTuning;
import org.apache.catalina.connector.Response;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class DummyController {

    @RequestMapping("/api/dummy")
    @AuthRole(roles = {Role.ADMIN, Role.ASPI})
    public String dummy(){
        return "Hello World";
    }

    @RequestMapping("/api/dummy/test")
    @AuthRole(roles = {Role.ASPI, Role.LEADER})
    public String dummy2(){
        return "Hello World";
    }
    //let this or tests will fail
    @RequestMapping("/dummy")
    public String dummy3(){
        return "Hello World";
    }



}