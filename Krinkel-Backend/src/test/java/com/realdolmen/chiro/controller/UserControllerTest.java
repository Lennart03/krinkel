package com.realdolmen.chiro.controller;


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
public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;


    @Test
    public void getUserReturnsUserGivenByUserService(){
        User u = new User("Ziggy", "test", "user", "ad1", "abcdefg");
        Mockito.when(userService.getUser("Ziggy", "test")).thenReturn(u);
        Assert.assertSame(controller.getUser("Ziggy", "test"), u);
    }
}
