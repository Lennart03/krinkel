package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.times;

//TODO rewrite after concurency is fixed
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private final static String TEST_AD_NUMBER = "123456";

    @Test
    public void controllerRequestsUserwWithCorrectAdNumber() {
        Mockito.when(service.getUser(Mockito.anyString())).thenReturn(new User());
        controller.getUser(TEST_AD_NUMBER);
        Mockito.verify(service, times(1)).getUser(TEST_AD_NUMBER);
    }

    @Test(expected = UserController.UserNotfoundException.class)
    public void controllerThrowsExceptionWhenUserIsNull() {
        Mockito.when(service.getUser(TEST_AD_NUMBER)).thenReturn(null);
        controller.getUser(TEST_AD_NUMBER);
    }

    @Test
    public void controllerReturnsUserGivenByService() {
        User u = new User();
        Mockito.when(service.getUser(Mockito.anyString())).thenReturn(u);
        User user = controller.getUser(TEST_AD_NUMBER);
        Assert.assertSame(u, user);
    }
}
