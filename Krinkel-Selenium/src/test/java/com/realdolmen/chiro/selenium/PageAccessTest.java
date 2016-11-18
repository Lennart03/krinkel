package com.realdolmen.chiro.selenium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example Selenium Test to verify configuration.
 */
public class PageAccessTest extends SeleniumTest {

    @Test
    public void notLoggedInUserIsRedirectedToCasPage() throws InterruptedException {
        driver().navigate().to(getBaseUrl() + "/login");
        assertEquals("https://login.chiro.be/user/login?destination=cas/login", driver().getCurrentUrl());
    }
}

