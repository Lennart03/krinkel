package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.config.CasConfiguration;
import com.realdolmen.chiro.service.CASService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CASController {

    @Autowired
    private CASService casService;

    @Autowired
    private CasConfiguration casConfiguration;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginRedirect(HttpServletRequest request) throws IOException {
        if (request.getHeader("Authorization") != null) {
            System.out.println("user wants to log in but already has authentication header");
            return "redirect:/index.html";
        } else {
            // Redirect to CAS server so the user can login.
            //if(true)throw new NullPointerException("ayy");
            System.out.println("redirecting user to login casService");
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
        String jwt = casService.validateTicket(ticket);
        if (jwt != null) {
            response.addCookie(casService.createCookie(jwt));
            response.setHeader("Authorization", jwt);
            response.sendRedirect("/index.html");
        }
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
    public ResponseEntity logout(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if (session != null) {
            System.out.println("session invalidating");
            session.invalidate();
            return new ResponseEntity(HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
