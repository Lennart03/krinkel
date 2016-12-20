package com.realdolmen.chiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableGlobalAuthentication
// Enables PreFilter, PostFilter, PostAuth, PreAuth annotations.
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityFilter extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/zip")
                .antMatchers("/api/cas")
                //.antMatchers("/api/**") // NEVER EVER UNCOMMENT THIS. PREVIOUSLY USED FOR DEV ON PORT 3000, THIS WILL SCREW EVERYTHING UP NOW.
                .antMatchers("/res/*")
                .antMatchers("/site/**")
                .antMatchers("/login")
                .antMatchers("/confirmation")
                .regexMatchers("/");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.setJwtConfiguration(jwtConfiguration);
        http
                .csrf().disable()
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Enforce https when security.require-ssl=true appears in application-*.properties.
        if (securityProperties.isRequireSsl()) {
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }
}