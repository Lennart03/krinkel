package com.realdolmen.chiro.chiro_api;


import org.junit.Assert;
import org.junit.Test;

public class ChiroUserAdapterTest {

    private ChiroUserAdapter adapter = new ChiroUserAdapter();

    @Test
    public void getUserReturnsUserWhenCredentialsAreCorrect() {
        Assert.assertNotNull(adapter.getUser("Ziggy", "test"));
    }

    @Test
    public void getUserReturnsNullWhenCredentialsAreFalse() {
        Assert.assertNull(adapter.getUser("Ziggy", "password"));
    }
}
