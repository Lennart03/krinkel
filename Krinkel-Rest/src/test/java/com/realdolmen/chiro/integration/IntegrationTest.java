package com.realdolmen.chiro.integration;

import org.junit.Before;

import static org.junit.Assume.assumeTrue;

/**
 * Abstract base class for Integration Tests.
 * Contains the common functionality to run or skip
 * the test based on a System property.
 *
 * To run with integration tests add -Dintegration
 *
 */
public abstract class IntegrationTest {

    private static final String INTEGRATION_ENABLED_SYSTEM_PROPERTY = "integration";

    @Before
    public void verifyIntegrationEnablingPreConditions(){
        boolean isIntegrationEnabled = (System.getProperty(IntegrationTest.INTEGRATION_ENABLED_SYSTEM_PROPERTY) != null);
        assumeTrue("Integration testing is disabled (enable using -Dintegration)", isIntegrationEnabled);
    }
}
