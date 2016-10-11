package com.realdolmen.chiro.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This controller will catch all 404 errors on the server and redirect to root.
 * Created by WVDAZ49 on 11/10/2016.
 */
@RestController
public class ChiroErrorController implements ErrorController{
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public void error(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
