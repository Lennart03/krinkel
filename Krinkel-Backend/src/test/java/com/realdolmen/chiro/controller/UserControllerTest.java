package com.realdolmen.chiro.controller;


import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private User u;

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Before
    public void init(){
        u = new User("Ziggy", "test", "user", "ad1", "abcdefg", true, "AG0103");
    }


    @Test
    public void getUserReturnsUserGivenByUserService(){
        Mockito.when(userService.getUser("Ziggy", "test")).thenReturn(u);
        Assert.assertSame(controller.getUser("Ziggy", "test"), u);
    }

    @Test(expected = Throwable.class)
    public void shouldThrowException(){
        Mockito.when(userService.getUser("Ziggy", "password")).thenReturn(null);
        controller.getUser("Ziggy", "password");
    }

    @Test
    public void shouldNotThrowException(){
        Mockito.when(userService.getUser("Ziggy", "test")).thenReturn(u);
        controller.getUser("Ziggy", "test");
    }
}
