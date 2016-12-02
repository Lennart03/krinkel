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

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void controllerRequestsUserwWithCorrectAdNumber() {
        Mockito.when(userService.getCurrentUser()).thenReturn(new User());
        userController.getUser();
        Mockito.verify(userService, times(1)).getCurrentUser();
    }

    @Test(expected = UserController.UserNotfoundException.class)
    public void controllerThrowsExceptionWhenUserIsNull() {
        Mockito.when(userService.getCurrentUser()).thenReturn(null);
        userController.getUser();
    }

    @Test
    public void controllerReturnsUserGivenByService() {
        User u = new User();
        Mockito.when(userService.getCurrentUser()).thenReturn(u);
        User user = userController.getUser();
        Assert.assertSame(u, user);
    }
}
