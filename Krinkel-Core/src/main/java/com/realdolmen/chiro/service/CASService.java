package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.CasConfiguration;
import com.realdolmen.chiro.config.JwtConfiguration;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Service
public class CASService {
    @Autowired
    private CasConfiguration configuration;

    @Autowired
    private JwtConfiguration jwtConfig;

    @Autowired
    private UserService userService;

    public String validateTicket(String ticket) {
        User user = validate(ticket);
        return createToken(user);
    }

    public final User validate(String ticket) {
        AttributePrincipal principal = null;
        Cas20ProxyTicketValidator sv = new Cas20ProxyTicketValidator(configuration.getBaseCasUrl());
        sv.setAcceptAnyProxy(true);

        try {
            Assertion a = sv.validate(ticket, configuration.getServiceUrl());
            principal = a.getPrincipal();
        } catch (TicketValidationException e) {
            throw new SecurityException("Ticket could not be validated");
        }
        if (principal != null) {

            /**
             * Instead of getting stuff from ChiroUserAdapter which is mock data, getting it from the principal which is exposed above ;)
             */

            String firstName = principal.getAttributes().get("first_name").toString();
            String lastName = principal.getAttributes().get("last_name").toString();
            String adNumber = principal.getAttributes().get("ad_nummer").toString();
            String email = principal.getAttributes().get("mail").toString();


            User user = new User();

            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setAdNumber(adNumber);
            user.setEmail(email);

            user.setRole(SecurityRole.ADMIN);
            userService.setCurrentUser(user);

            return user;
            // TODO: in an ideal world, cookie should only contain ad-number (and mayhaps role)
//            return userService.getUser(
//                    principal.getAttributes().get("ad_nummer").toString()
//            );
        }
        return null;
    }

    public Boolean hasRole(final SecurityRole[] roles, final HttpServletRequest request) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtConfig.getJwtSecret()))
                .parseClaimsJws(getTokenFromCookie(request.getCookies())).getBody();
        if (claims != null) {
            for(SecurityRole role:roles){
                if (claims.get("role").toString().equals(role.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Cookie createCookie(String jwt){
        Cookie myCookie = new Cookie("Authorization", jwt);
        myCookie.setPath("/");
        myCookie.setMaxAge(-1);
        return myCookie;
    }

    protected String getTokenFromCookie(Cookie[] cookies) {
        for (Cookie c : cookies) {
            if (c.getName().equals("Authorization")) {
                return c.getValue();
            }
        }
        return null;
    }

    public String createToken(User user) {
        //newLogin(data);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("firstname", user.getFirstname())
                .claim("lastname", user.getLastname())
                .claim("adnummer", user.getAdNumber())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getJwtSecret()).compact();
    }
}
