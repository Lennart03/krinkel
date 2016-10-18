package com.realdolmen.chiro.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("Authorization");
        String authCookieToken = null;
        if(request.getCookies()!=null){
             authCookieToken = getTokenFromCookie(request.getCookies());
        }

        String token;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            //second way of authentication is with the cookie set by the server
            if(authCookieToken!=null){
                token = authCookieToken;
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }else{
            token = authHeader.substring(7); // The part after "Bearer "
        }

        try {
            final Claims claims = Jwts.parser().setSigningKey("MATHIASISNOOB")
                    .parseClaimsJws(token).getBody();
            request.setAttribute("claims", claims);
        }
        catch (final SignatureException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        chain.doFilter(req, res);
    }

    /**
     * @param cookies list of cookies from the request
     * @return Authorization cookie value (JWT)
     */
    protected String getTokenFromCookie(Cookie[] cookies){
        for(Cookie c:cookies){
            if(c.getName().equals("Authorization")){
                return c.getValue();
            }
        }
        return null;
    }

}