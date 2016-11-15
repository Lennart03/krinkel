package com.realdolmen.chiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityFilter extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/api/cas")
                //.antMatchers("/api/**") //UNCOMMENT THIS FOR TESTING ON PORT 3000
                .antMatchers("/res/*")
                .antMatchers("/site/**")
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
    }
}