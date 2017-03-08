package com.realdolmen.chiro.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value=RuntimeException.class)
    protected String returnCustomErrorPage(RuntimeException rtex, WebRequest req){
        rtex.printStackTrace();
        return "redirect:http://rdkrinkeltest.westeurope.cloudapp.azure.com/site/error.html";
    }
}
