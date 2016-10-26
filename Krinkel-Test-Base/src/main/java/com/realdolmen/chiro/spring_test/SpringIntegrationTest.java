package com.realdolmen.chiro.spring_test;

import com.realdolmen.chiro.TestApplication;
import com.realdolmen.chiro.integration.IntegrationTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Convenience super class for writing tests which use the SpringJunitRunner.
 * Sets the necessary configuration properties to run successfully.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestApplication.class}) // Spring Boot config (includes component scan)
@Transactional // Enables rollback after each test.
@TestPropertySource(locations="classpath:application-test.properties") // Different set of properties to set H2 as DB.
@ActiveProfiles("test")
public abstract class SpringIntegrationTest extends IntegrationTest {
}
