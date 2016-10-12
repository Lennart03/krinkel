package com.realdolmen.chiro.controller;

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
    public String dummy(){
        return "Hello World";
    }

    @RequestMapping("/dummy")
    public String dummy2(){
        return "Hello World";
    }



}