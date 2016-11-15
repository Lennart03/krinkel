package com.realdolmen.chiro.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtFilterMockitoTest {

    @Mock
    private JwtConfiguration jwtConfiguration;

    @InjectMocks
    private JwtFilter jwtFilter;

    private Cookie[] cookies;

    private String AUTHCOOKIE = "erbsmoqjqsfczkkjpzoeezqlffruezqcizuyvirtfszeoiaqé";

    @Before
    public void setUp(){
        Cookie cookie = new Cookie("Authorization", AUTHCOOKIE);
        cookies = new Cookie[]{cookie};

        when(jwtConfiguration.getJwtSecret()).thenReturn("abab");
    }

    @Test
    public void testDoFilterWithNoCookiesDoesNotProceed() throws IOException, ServletException {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        jwtFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verifyZeroInteractions(filterChain);
    }
}
