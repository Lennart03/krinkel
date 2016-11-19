package com.realdolmen.chiro.auth;

import com.realdolmen.chiro.auth.AuthRole;
import com.realdolmen.chiro.service.CASService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Checks the role of the logged in user for methods annotated with @AuthRole.
 */
@Component
@Aspect
public class  AuthRoleFilter {
    @Autowired
    private CASService service;

    @Around(value = "@annotation(annotation)")
    public Object checkAuthRole(final ProceedingJoinPoint jp,final AuthRole annotation) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if(!service.hasRole(annotation.roles(), request)){
            throw new SecurityException();
        }
        else{
            return jp.proceed();
        }
    }
}
