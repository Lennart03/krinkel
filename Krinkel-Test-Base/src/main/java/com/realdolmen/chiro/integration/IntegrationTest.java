package com.realdolmen.chiro.integration;

import org.junit.BeforeClass;

import static org.junit.Assume.assumeFalse;

/**
 * Abstract base class for Integration Tests.
 *
 * Contains the common functionality to run or skip
 * the test based on a System property.
 *
 * Integration tests are run by default.
 * To run without integration tests add -DskipIntegration
 *
 */
public abstract class IntegrationTest {

    private static final String INTEGRATION_DISABLED_SYSTEM_PROPERTY = "skipIntegration";

    @BeforeClass
    public static void verifyIntegrationEnablingPreConditions(){
        boolean isIntegrationDisabled = (System.getProperty(IntegrationTest.INTEGRATION_DISABLED_SYSTEM_PROPERTY) != null);
        assumeFalse("Integration testing is disabled", isIntegrationDisabled);
    }
}
