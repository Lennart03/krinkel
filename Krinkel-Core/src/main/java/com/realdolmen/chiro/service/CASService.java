package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.CasConfiguration;
import com.realdolmen.chiro.config.JwtConfiguration;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.domain.vo.StamNumbersRolesVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.*;

@Service
public class CASService {
    @Autowired
    private CasConfiguration casConfiguration;

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogAdvice loginLogAdvice;

    @Autowired
    private AdminService adminService;

    public String validateTicket(String ticket) {
        User user = validate(ticket);
        return createToken(user);
    }

    /**
     * Validates the CAS ticket. The current CAS user is set from this method.
     *
     * @param ticket
     * @return
     */
    public final User validate(String ticket) {
        AttributePrincipal principal = null;
        Cas20ProxyTicketValidator sv = new Cas20ProxyTicketValidator(casConfiguration.getBaseCasUrl());
        sv.setAcceptAnyProxy(true);

        try {
            Assertion a = sv.validate(ticket, casConfiguration.getServiceUrl());
            principal = a.getPrincipal();
        } catch (TicketValidationException e) {
            throw new SecurityException("Ticket could not be validated");
        }
        if (principal != null) {

            /*
             * Instead of getting stuff from ChiroUserAdapter which is mock data, getting it from the principal which is exposed above ;)
             */
            String adNumber = principal.getAttributes().get("ad_nummer").toString();

            User user;
            RegistrationParticipant registrationParticipantFromOurDB = userService.getRegistrationParticipant(adNumber);

            /*
             *  Checks if the logged in user is in our DB (user has already paid or at least tried to initializePayment)
             */
            if (registrationParticipantFromOurDB != null) {
                user = createUserFromOurDB(registrationParticipantFromOurDB);
            } else {
                /*
                 * Reached when the logged in user is not in our DB (this means he hasn't tried to initializePayment yet)
                 */
                user = createNewUser(principal, adNumber);
            }

            StamNumbersRolesVO stamNumbersRolesVO = findAllStamNumbersAndRoles(adNumber);

            user.setRolesAndUpperClassesByStam(stamNumbersRolesVO.getRolesAndUpperClassesByStam());
            user.setStamnummer(stamNumbersRolesVO.getStamNumber());
            
          List<String> adminAdNumbers = new ArrayList<>();
           for(Admin admin : adminService.getAdmins()){
               adminAdNumbers.add(admin.getAdNummer().toString());
           }


            if (adminAdNumbers.contains(adNumber)) {
                user.setRole(SecurityRole.ADMIN);
            } else {
                user.setRole(SecurityRole.GROEP);
            }

            if(userService.getCurrentUser() == null || userService.getCurrentUser().getRole() == null || !userService.getCurrentUser().getRole().equals(SecurityRole.ADMIN)) {
                userService.setCurrentUser(user);
            }

            return user;
        }
        return null;
    }

    private User createUserFromOurDB(RegistrationParticipant registrationParticipant){
        User user = new User();
        user.setFirstname(registrationParticipant.getFirstName());
        user.setLastname(registrationParticipant.getLastName());
        user.setAdNumber(registrationParticipant.getAdNumber());
        user.setEmail(registrationParticipant.getEmail());

                /*
                 * Set hasPaid based on status
                 */
        if (registrationParticipant.getStatus().equals(Status.PAID) || registrationParticipant.getStatus().equals(Status.CONFIRMED)) {
            user.setHasPaid(true);
        } else {
            user.setHasPaid(false);
        }

        user.setRegistered(true);

        return user;
    }

    private User createNewUser(AttributePrincipal principal, String adNumber){
        User user = new User();
        String firstName = principal.getAttributes().get("first_name").toString();
        String lastName = principal.getAttributes().get("last_name").toString();
        String email = principal.getAttributes().get("mail").toString();

        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setAdNumber(adNumber);
        user.setEmail(email);
        user.setRegistered(false);
        user.setHasPaid(false);

        return user;
    }

    private StamNumbersRolesVO findAllStamNumbersAndRoles(String adNumber) {
        return userService.getAllStamnumbersRolesAndUpperUnits(adNumber);
    }


    public Boolean hasRole(final SecurityRole[] roles, final HttpServletRequest request) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtConfiguration.getJwtSecret()))
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
            if ("Authorization".equals(c.getName())) {
//            if (c.getName().equals("Authorization")) {
                return c.getValue();
            }
        }
        return null;
    }

    public String createToken(User user) {
        loginLogAdvice.registerLoginAfterTokenCreation(user);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("firstname", user.getFirstname())
                .claim("lastname", user.getLastname())
                .claim("adnummer", user.getAdNumber())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtConfiguration.getJwtSecret()).compact();
    }
}
