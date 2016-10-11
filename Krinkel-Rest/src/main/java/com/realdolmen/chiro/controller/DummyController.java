package com.realdolmen.chiro.controller;

import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/{[path:[^\\.]*}")
    public Response redirect() throws IOException {
        Response response = new Response();
        response.sendRedirect("/");
        return response;
    }
}
