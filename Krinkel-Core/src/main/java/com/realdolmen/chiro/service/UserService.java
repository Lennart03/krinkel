package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.config.JwtConfiguration;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

@Service("userService")
@Profile("!test")
public class UserService {
    @Autowired
    private ChiroUserAdapter adapter;

    @Autowired
    private RegistrationParticipantRepository repo;

    @Autowired
    private JwtConfiguration jwtConfig;

    public User getUser(String adNumber) {

        User u = adapter.getChiroUser(adNumber);

        if (u != null) {
            RegistrationParticipant participant = repo.findByAdNumber(u.getAdNumber());

            if (participant != null) {
                u.setRegistered(true);

                if (participant.getStatus() == Status.PAID)
                    u.setHasPaid(true);
            }

            this.setSecurityRole(u);
        }

        return u;
    }

    private void setSecurityRole(User u) {
        if (u.getStamnummer() == null) return;
        if (u.getRole() != null && u.getRole() == SecurityRole.ADMIN) return;

        String stamNummer = u.getStamnummer();

        if (stamNummer.matches("NAT\\/0000")) {
            u.setRole(SecurityRole.NATIONAAL);
        } else if (stamNummer.matches("[A-Z]+ /0000")) {
            u.setRole(SecurityRole.VERBOND);
        } else if (stamNummer.matches("[A-Z]{3}/[0-9]{2}00") || stamNummer.matches("[A-Z]{2} /[0-9]{2}00")) {
            u.setRole(SecurityRole.GEWEST);
        } else {
            u.setRole(SecurityRole.GROEP);
        }
    }

    public User getCurrentUser(HttpServletRequest context){
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtConfig.getJwtSecret()))
                .parseClaimsJws(getTokenFromCookie(context.getCookies())).getBody();


        String adnumber = claims.get("adnummer").toString();

        return this.getUser(adnumber);
    }

    public User getCurrentUser(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest context = requestAttributes.getRequest();
        return this.getCurrentUser(context);
    }

    protected String getTokenFromCookie(Cookie[] cookies){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("Authorization")){
                return cookie.getValue();
            }
        }
        return null;
    }
}
