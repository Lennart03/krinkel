package com.realdolmen.chiro.config;

import com.realdolmen.chiro.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by WVDAZ49 on 12/10/2016.
 */
@Component
@Aspect
public class  AuthRoleFilter {
    @Autowired
    UserService service;
    @Around(value = "@annotation(annotation)")
    public void checkAuthRole(final AuthRole annotation) throws Throwable {
        System.out.println(annotation.roles());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if(!service.hasRole(annotation.roles(), request)){
            ServletWebRequest servletWebRequest=new ServletWebRequest(request);
            HttpServletResponse response = servletWebRequest.getResponse();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
