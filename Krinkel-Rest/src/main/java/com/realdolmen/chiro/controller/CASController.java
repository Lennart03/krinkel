package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.config.CasConfiguration;
import com.realdolmen.chiro.service.CASService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CASController {
    @Autowired
    private CASService service;

    @Autowired
    private CasConfiguration casConfiguration;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginRedirect(HttpServletRequest request) throws IOException {
        if (request.getHeader("Authorization") != null) {
            System.out.println("user wants to log in but already has authentication header");
            return "redirect:/index.html";
        } else {
            // Redirect to CAS server so the user can login.
            System.out.println("redirecting user to login service");
            return "redirect:" + casConfiguration.getCasRedirectUrl();
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewer(HttpServletRequest request) throws IOException {
        if (request.getHeader("Authorization") != null) {
            return "redirect:/index.html";
        } else {
            return "redirect:/site/index.html";
        }
    }


    @RequestMapping(value = "/api/cas", method = RequestMethod.GET)
    public void casRedirect(@RequestParam String ticket, HttpServletResponse response) throws IOException {
        String jwt = service.validateTicket(ticket);
        if (jwt != null) {
            response.addCookie(service.createCookie(jwt));
            response.setHeader("Authorization", jwt);
            response.sendRedirect("/index.html");
        }
    }
}
