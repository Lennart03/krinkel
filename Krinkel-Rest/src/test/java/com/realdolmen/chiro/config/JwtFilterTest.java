package com.realdolmen.chiro.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;

public class JwtFilterTest {
    private Cookie cookie;
    private Cookie[] cookies;
    private JwtFilter jwtFilter;

    private String AUTHCOOKIE = "erbsmoqjqsfczkkjpzoeezqlffruezqcizuyvirtfszeoiaqé";

    @Before
    public void setUp(){
        cookie = new Cookie("Authorization", AUTHCOOKIE);
        cookies = new Cookie[1];
        cookies[0] = cookie;
        jwtFilter = new JwtFilter();
    }

    @Test
    public void testCookieShouldSucceed(){
        Assert.assertEquals(AUTHCOOKIE, jwtFilter.getTokenFromCookie(cookies));
    }

    // TODO: Remove test, already checked by #testCookieShouldSucceed().
    @Test
    public void testCookieShouldFail(){
        Assert.assertNotEquals(AUTHCOOKIE.substring(9), jwtFilter.getTokenFromCookie(cookies));
    }
}
