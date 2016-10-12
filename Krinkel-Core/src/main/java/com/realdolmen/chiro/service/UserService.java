package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Service
public class UserService {

    public static final String  CASURL = "https://login.chiro.be/cas/login?service=http://localhost:8080/api/cas";

    public String validateTicket(String ticket){
        User user = validate(ticket);
        return createToken(user);
    }

    public final User validate(String ticket) {
        AttributePrincipal principal = null;
        String casServerUrl = "https://login.chiro.be/cas/";
        Cas20ProxyTicketValidator sv = new Cas20ProxyTicketValidator(casServerUrl);
        sv.setAcceptAnyProxy(true);
        try {
            String legacyServerServiceUrl = "http://localhost:8080/api/cas";
            Assertion a = sv.validate(ticket, legacyServerServiceUrl);
            principal = a.getPrincipal();
        } catch (TicketValidationException e) {
            throw new SecurityException("Ticket could not be validated");
        }
        if (principal != null){
            User user = new User();
            user.setEmail(principal.getAttributes().get("mail").toString());
            user.setUsername(principal.getName().toString());
            user.setFirstname(principal.getAttributes().get("first_name").toString());
            user.setLastname(principal.getAttributes().get("last_name").toString());
            user.setAdNumber(principal.getAttributes().get("ad_nummer").toString());
            //TODO: implement this for real data & persons
            user.setRole(Role.ADMIN);
            return user;
        }
        return null;
    }
    public Boolean hasRole(final Role role, final HttpServletRequest request){
        final Claims claims = (Claims) request.getAttribute("claims");

        return ((List<String>) claims.get("role")).contains(role);
    }

    public String createToken(User data) {
        return Jwts.builder()
                .setSubject(data.getUsername())
                .claim("firstname", data.getFirstname())
                .claim("lastname", data.getLastname())
                .claim("adnummer", data.getAdNumber())
                .claim("email", data.getEmail())
                .claim("role", data.getRole())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "MATHIASISNOOB").compact();
    }
}
