package com.realdolmen.chiro.config;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JwtFilterTest {
    Cookie cookie;
    Cookie[] cookies;
    JwtFilter jwtFilter;

    String AUTHCOOKIE = "erbsmoqjqsfczkkjpzoeezqlffruezqcizuyvirtfszeoiaqé";

    @Before
    public void setUp(){
        cookie = new Cookie("Authorization", AUTHCOOKIE );
        cookies = new Cookie[1];
        cookies[0] = cookie;
        jwtFilter = new JwtFilter();
    }


    @Test
    public void testCookieShouldSucceed(){
        Assert.assertEquals(jwtFilter.getTokenFromCookie(cookies), AUTHCOOKIE);
    }

    @Test
    public void testCookieShouldFail(){
        Assert.assertNotEquals(jwtFilter.getTokenFromCookie(cookies), AUTHCOOKIE.substring(9));
    }
}
