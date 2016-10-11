package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.Role;
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
import java.util.*;

@Controller
public class UserController {

    @Autowired
    UserService service;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewer(HttpServletRequest request) {



        System.out.println(request.getRequestURI());

        return "redirect:/index.html";
    }




}
