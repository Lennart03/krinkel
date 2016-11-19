package com.realdolmen.chiro.config;

import com.realdolmen.chiro.auth.AuthToken;
import com.realdolmen.chiro.auth.AuthTokenFinder;
import com.realdolmen.chiro.auth.AuthTokenNotFoundException;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JSON Web Tokens Security Filter.
 *
 * Usage:
 * JwtFilter filter = new JwtFilter();
 * filter.setJwtConfiguration(jwtConfig);
 *
 */
public class JwtFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private JwtConfiguration jwtConfiguration;

    private AuthTokenFinder authTokenFinder = new AuthTokenFinder();

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        try {
            AuthToken authToken = authTokenFinder.findAuthenticationToken(request);

            String secret = jwtConfiguration.getJwtSecret();
            final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(authToken.getValue())
                .getBody();
            request.setAttribute("claims", claims);

            chain.doFilter(req, res);
        }
        catch (final SignatureException | AuthTokenNotFoundException e) {
            logger.debug("Invalid login attempt: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public JwtConfiguration getJwtConfiguration() {
        return jwtConfiguration;
    }

    public void setJwtConfiguration(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }
}