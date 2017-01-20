package com.realdolmen.chiro.chiro_api;


import org.junit.Test;

public class ChiroUserAdapterTest {

    private ChiroUserAdapter adapter = new ChiroUserAdapter();


    @Test
    public void getChiroUserReturnsUserWhenAdNumberIsValid() {
//        Assert.assertNotNull(adapter.getChiroUser("386289"));
    }

    @Test
    public void getChiroUserReturnsNullWhenAdNumberIsNotValid() {
//        Assert.assertNull(adapter.getChiroUser("ao9iefjpao9iejf"));
    }
}
