package com.realdolmen.chiro.config;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AuthTokenFinderTest {

    private String AUTHCOOKIE = "erbsmoqjqsfczkkjpzoeezqlffruezqcizuyvirtfszeoiaqé";

    private Cookie[] cookies;

    private AuthTokenFinder authTokenFinder;

    @Before
    public void setUp(){
        authTokenFinder = new AuthTokenFinder();

        Cookie cookie = new Cookie("Authorization", AUTHCOOKIE);
        cookies = new Cookie[]{cookie};
    }

    @Test(expected = AuthTokenNotFoundException.class)
    public void testGetAuthenticationCookieWithNullGivesNull() throws AuthTokenNotFoundException {
        Cookie result = authTokenFinder.getAuthenticationCookie(null);
    }

    @Test
    public void testGetAuthenticationCookieWithSingleCookieReturnsCorrectCookie() throws AuthTokenNotFoundException {
        Cookie authCookie = authTokenFinder.getAuthenticationCookie(cookies);

        assertNotNull(authCookie);
        assertEquals(AUTHCOOKIE, authCookie.getValue());
    }
}
