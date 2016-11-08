package com.realdolmen.chiro.controller.security;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerSecurityTest {
    @InjectMocks
    private UserControllerSecurity userControllerSecurity;

    @Mock
    private UserService userService;

    @Test
    public void noAdNumberReturnsFalse(){
        boolean b = userControllerSecurity.hasPermissionToGetUser(null);
        Assert.assertEquals(false, b);
    }

    @Test
    public void adNumbersNotEqualReturnsFalse(){
        User currentUser = new User();
        currentUser.setAdNumber("1234");
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        boolean b = userControllerSecurity.hasPermissionToGetUser("123");
        Assert.assertEquals(false, b);
    }

    @Test
    public void addNumbersEqualsReturnsTrue(){
        User currentUser = new User();
        currentUser.setAdNumber("123");
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        boolean b = userControllerSecurity.hasPermissionToGetUser("123");
        Assert.assertEquals(true, b);
    }


}
