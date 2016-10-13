package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.service.CASService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by WVDAZ49 on 12/10/2016.
 */
@Controller
public class CASController {
    @Autowired
    CASService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewer(HttpServletRequest request) throws IOException {
        if (request.getHeader("Authorization")!=null){
            return "redirect:/index.html";
        } else {
            return "redirect:" + service.CASURL;
        }
    }

    @RequestMapping(value = "/api/cas", method = RequestMethod.GET)
    public void casRedirect(@RequestParam String ticket, HttpServletResponse response) throws IOException {
        String jwt = service.validateTicket(ticket);
        if (jwt!=null){
            response.addCookie(service.createCookie(jwt));
            response.setHeader("Authorization", jwt);
            response.sendRedirect("/index.html");
        }
    }
}
