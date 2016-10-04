package com.realdolmen.chiro.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

    private UserService service;

    @Before
    public void init() {
        service = new UserService();
    }

    @Test
    public void getUserReturnsUserWhenCredentialsAreCorrect() {
        Assert.assertNotNull(service.getUser("Ziggy", "test"));
    }

    @Test
    public void getUserReturnsNullWhenCredentialsAreFalse() {
        Assert.assertNull(service.getUser("Ziggy", "password"));
    }
}
