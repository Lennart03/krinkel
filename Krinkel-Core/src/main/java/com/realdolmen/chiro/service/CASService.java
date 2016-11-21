package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.CasConfiguration;
import com.realdolmen.chiro.config.JwtConfiguration;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.Status;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CASService {
    @Autowired
    private CasConfiguration configuration;

    @Autowired
    private JwtConfiguration jwtConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private ChiroPloegService chiroPloegService;



    public String validateTicket(String ticket) {
        User user = validate(ticket);
        return createToken(user);
    }

    /**
     * Validates the CAS ticket. The current CAS user is set from this method.
     * @param ticket
     * @return
     */
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
            String adNumber = principal.getAttributes().get("ad_nummer").toString();

            User user = new User();
            RegistrationParticipant registrationParticipantFromOurDB = userService.getRegistrationParticipant(adNumber);

            /**
             *  Checks if the logged in user is in our DB (user has already paid or at least tried to pay)
             */
            if (registrationParticipantFromOurDB != null) {
                user.setFirstname(registrationParticipantFromOurDB.getFirstName());
                user.setLastname(registrationParticipantFromOurDB.getLastName());
                user.setAdNumber(registrationParticipantFromOurDB.getAdNumber());
                user.setEmail(registrationParticipantFromOurDB.getEmail());

                /**
                 * Set hasPaid based on status
                 */
                if (registrationParticipantFromOurDB.getStatus().equals(Status.PAID) || registrationParticipantFromOurDB.getStatus().equals(Status.CONFIRMED)) {
                    user.setHasPaid(true);
                } else {
                    user.setHasPaid(false);
                }

                user.setRegistered(true);
            } else {
                /**
                 * Reached when the logged in user is not in our DB (this means he hasn't tried to pay yet)
                 */
                String firstName = principal.getAttributes().get("first_name").toString();
                String lastName = principal.getAttributes().get("last_name").toString();
                String email = principal.getAttributes().get("mail").toString();

                user.setFirstname(firstName);
                user.setLastname(lastName);
                user.setAdNumber(adNumber);
                user.setEmail(email);
                user.setRegistered(false);
                user.setHasPaid(false);
            }

            user.setRole(setCorrectSecurityRole(adNumber));
            userService.setCurrentUser(user);

            return user;
        }
        return null;
    }

    /**
     * Sets the correct security role of the currently logged in user
     * @param adNumber
     * @return
     */
    private SecurityRole setCorrectSecurityRole(String adNumber) {
        //TODO change this hardcoded list of admins (in db & stuff)
        List<String> adminAdNumbers = new ArrayList<>();
//        adminAdNumbers.add("152504");
//        adminAdNumbers.add("109318");
        adminAdNumbers.add("169314");
        adminAdNumbers.add("386288");

        if (adminAdNumbers.contains(adNumber)) {
            return SecurityRole.ADMIN;
        } else {
            List<String> stamNumbers = chiroPloegService.getStamNumbers(adNumber);
            return userService.getHighestSecurityRole(stamNumbers);
        }
    }



    public Boolean hasRole(final SecurityRole[] roles, final HttpServletRequest request) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtConfig.getJwtSecret()))
                .parseClaimsJws(getTokenFromCookie(request.getCookies())).getBody();
        if (claims != null) {
            for (SecurityRole role : roles) {
                if (claims.get("role").toString().equals(role.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Cookie createCookie(String jwt) {
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
