package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.LoginLog;
import com.realdolmen.chiro.domain.User;


import com.realdolmen.chiro.repository.LoginLoggerRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginLogAdvice {

    @Autowired
    private LoginLoggerRepository loginLoggerRepository;

    /**
     * Register that a login has occurred for reporting purposes.
     *
     * @param user
     */
    @Before("execution(String com.realdolmen.chiro.service.CASService.createToken(com.realdolmen.chiro.domain.User)) && args(user)")
    public void registerLoginAfterTokenCreation(User user) {
        loginLoggerRepository.save(
                new LoginLog(user.getAdNumber(), user.getStamnummer())
        );
    }
}
