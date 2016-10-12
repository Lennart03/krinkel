package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;

public class UserServiceTest {
    User user;
    CASService casService;

    @Before
    public void setUp(){
        casService = new CASService();
        user = new User();
        user.setAdNumber("ADBUmBER");
        user.setEmail("john.doe@example.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setRole(Role.MENTOR);
        user.setUsername("Franske4653");
    }

    @Test
    public void testUserService()  {
        String token = casService.createToken(user);
        Jwt jwt = Jwts.parser().setSigningKey(casService.JWT_SECRET).parse(token);
        Assert.assertEquals(jwt.getBody().toString().substring(0, jwt.getBody().toString().lastIndexOf(",")), "{sub=Franske4653, firstname=John, lastname=Doe, adnummer=ADBUmBER, email=john.doe@example.com, role=MENTOR");
        Assert.assertEquals(jwt.getHeader().toString(), "{alg=HS256}");
    }
}
