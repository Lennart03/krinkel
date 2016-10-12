package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.User;
import io.jsonwebtoken.Jwt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.jsonwebtoken.Jwts;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService service;

    @Mock
    private ChiroUserAdapter adapter;

    private final static String TEST_AD_NUMBER = "apiuehf54aiawuef";

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


    @Test
    public void getUserAsksChiroUserAdapterForUserWithCorrectAdNumber() {
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(new User());
        service.getUser(TEST_AD_NUMBER);
        Mockito.verify(adapter, times(1)).getChiroUser(TEST_AD_NUMBER);
    }


    @Test
    public void getUserReturnsUserGivenByChiroAdapter() {
        User u = new User();
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(u);
        User user = service.getUser(TEST_AD_NUMBER);
        Assert.assertSame(user, u);
    }
}
