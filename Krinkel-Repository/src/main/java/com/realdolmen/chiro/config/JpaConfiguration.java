package com.realdolmen.chiro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages="com.realdolmen.chiro.repository")
public class JpaConfiguration {

	public JpaConfiguration() {
		// TODO Auto-generated constructor stub
	}

}
