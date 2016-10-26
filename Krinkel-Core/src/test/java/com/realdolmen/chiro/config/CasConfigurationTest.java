package com.realdolmen.chiro.config;

import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class CasConfigurationTest extends SpringIntegrationTest {

    @Autowired
    private CasConfiguration configuration;

    /*
       The following set of tests confirm that the Spring @Value annotations correctly copy
       configuration properties to the instance variables of the Service.
     */
    //<editor-fold desc="Autowired config properties">
    @Test
    public void casBaseUrlIsSetCorrectly(){
        assertEquals("https://login.chiro.be/cas/", configuration.getBaseCasUrl());
    }

    @Test
    public void casServiceUrlIsSetCorrectly(){
        assertEquals("http://localhost:8080/api/cas", configuration.getServiceUrl());
    }

    @Test
    public void casRedirectUrlIsGeneratedCorrectly(){
        String expectedRedirectUrl = "https://login.chiro.be/cas/login?service=http://localhost:8080/api/cas";
        assertEquals(expectedRedirectUrl, configuration.getCasRedirectUrl());
    }
    //</editor-fold>
}
