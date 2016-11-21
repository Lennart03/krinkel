package com.realdolmen.chiro.service.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> log() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
