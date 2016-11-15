package com.realdolmen.chiro.config;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AuthTokenFinderTest {
    private Cookie[] cookies;
    private Cookie authCookie;

    private AuthTokenFinder authTokenFinder;

    @Before
    public void setUp(){
        authTokenFinder = new AuthTokenFinder();
        authCookie = new Cookie("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJmaXJzdG5hbWUiOiJWaW5jZW50IiwibGFzdG5hbWUiOiJDZXVsZW1hbnMiLCJhZG51bW1lciI6IjM4NjI5MiIsImVtYWlsIjoidmluY2VudC5jZXVsZW1hbnNAcmVhbGRvbG1lbi5jb20iLCJyb2xlIjoiR1JPRVAiLCJpYXQiOjE0NzkyMjI5MzN9.xR5CcRQWud1UoaY3dZ3c4KOVLx-lqFe_HUtsfdNtOBw");
        cookies = new Cookie[]{authCookie};
    }

    @Test(expected = AuthTokenNotFoundException.class)
    public void testGetAuthenticationCookieWithNullGivesNull() throws AuthTokenNotFoundException {
        Cookie result = authTokenFinder.getAuthenticationCookie(null);
    }

    @Test
    public void testGetAuthenticationCookieWithSingleCookieReturnsCorrectCookie() throws AuthTokenNotFoundException {
        Cookie result = authTokenFinder.getAuthenticationCookie(cookies);

        assertNotNull(result);
        assertEquals(authCookie.getValue(), result.getValue());
    }
}
