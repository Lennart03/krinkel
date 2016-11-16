package com.realdolmen.chiro.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.After;
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
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtFilterMockitoTest {

    @Mock
    private JwtConfiguration jwtConfiguration;

    @InjectMocks
    private JwtFilter jwtFilter;

    private Cookie[] cookies;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Before
    public void setUp(){
        when(jwtConfiguration.getJwtSecret()).thenReturn("abab");

        // Build an authorization token for use in this unit test.
        JwtBuilder builder = Jwts.builder()
                .setId("c")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtConfiguration.getJwtSecret());

        Cookie cookie = new Cookie("Authorization", builder.compact());
        cookies = new Cookie[]{cookie};
    }

    @After
    public void verifyStrict(){
        Mockito.verifyNoMoreInteractions(filterChain);
        Mockito.verifyNoMoreInteractions(request);
        Mockito.verifyNoMoreInteractions(response);
    }

    @Test
    public void testDoFilterWithCookiesNullDoesNotProceed() throws IOException, ServletException {
        Mockito.when(request.getCookies()).thenReturn(null);
        Mockito.when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilter(request, response, filterChain);

        Mockito.verify(request).getCookies();
        Mockito.verify(request).getHeader("Authorization");
        Mockito.verify(response).setStatus(Mockito.any(Integer.class));
        Mockito.verifyZeroInteractions(filterChain);
    }

    @Test
    public void testDoFilterWithValidCookieProceedsToNextFilterInChain() throws IOException, ServletException {
        Mockito.when(request.getCookies()).thenReturn(cookies);
        Mockito.when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilter(request, response, filterChain);

        Mockito.verify(request).getCookies();
        Mockito.verify(request).getHeader("Authorization");
        Mockito.verify(request).setAttribute(Mockito.eq("claims"), Mockito.any(Claims.class));

        Mockito.verify(filterChain).doFilter(request, response);
    }
}