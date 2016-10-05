package com.realdolmen.chiro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.realdolmen.chiro")
@Configuration
public class AppConfig {

}
