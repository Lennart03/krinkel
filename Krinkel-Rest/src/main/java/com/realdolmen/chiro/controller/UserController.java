package com.realdolmen.chiro.controller;

import javax.servlet.*;
import javax.servlet.http.*;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import com.sun.media.sound.SoftTuning;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;


    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{adNumber}")
    public User getUser(@PathVariable("adNumber") String adNumber) throws UserNotfoundException {
        User u = service.getUser(adNumber);

        if ( u == null )
            throw new UserNotfoundException();

        return u;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class UserNotfoundException extends RuntimeException {
        public UserNotfoundException() {
            super("Gebruiker bestaat niet.");
        }
    }
}
