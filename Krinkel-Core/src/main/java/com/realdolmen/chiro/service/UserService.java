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

    public final boolean validateTicket(String ticket) {
        AttributePrincipal principal = null;
        String casServerUrl = "https://login.chiro.be/cas/";
        Cas20ProxyTicketValidator sv = new Cas20ProxyTicketValidator(casServerUrl);
        sv.setAcceptAnyProxy(true);
        try {
            String legacyServerServiceUrl = "http://localhost:8080/api/cas";
            Assertion a = sv.validate(ticket, legacyServerServiceUrl);
            principal = a.getPrincipal();
            System.out.println("user name:" + principal.getName());
            System.out.println(principal.getAttributes().get("ad_nummer"));
            System.out.println(a.isValid());
        } catch (TicketValidationException e) {
            e.printStackTrace(); // bad style, but only for demonstration purpose.
        }
        return principal != null;
    }
    public Boolean hasRole(final Role role, final HttpServletRequest request) throws ServletException {
        final Claims claims = (Claims) request.getAttribute("claims");

        return ((List<String>) claims.get("role")).contains(role);
    }

    public String CreateToken(User data) throws ServletException {
        return Jwts.builder()
                .setSubject(data.getUsername())
                .claim("username", data.getUsername())
                .claim("firstname", data.getFirstname())
                .claim("lastname", data.getLastname())
                .claim("adnummer", data.getAdNumber())
                .claim("email", data.getEmail())
                .claim("subscribed", data.getSubscribed())
                .claim("role", data.getRole())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "MATHIASISNOOB").compact();
    }
}
