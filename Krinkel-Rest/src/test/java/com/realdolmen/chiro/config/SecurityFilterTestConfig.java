package com.realdolmen.chiro.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Disables the security checks during testing.
 * The @PreAuthorize, @PostAuthorize, @PreFilter and @PostFilter annotations
 * will simply be ignored.
 *
 * Add @ContextConfiguration(classes = {SecurityFilterTestConfig.class}) at the top
 * of the test class to enable this config.
 */
@Configuration
@Profile("test")
@EnableGlobalMethodSecurity(prePostEnabled = false)
public class SecurityFilterTestConfig {
}