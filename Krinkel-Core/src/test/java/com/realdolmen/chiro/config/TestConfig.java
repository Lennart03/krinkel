package com.realdolmen.chiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.realdolmen.chiro.service.EmailSenderService;
import com.realdolmen.chiro.service.EmailSenderServiceImpl;

@Configuration
public class TestConfig {

	@Bean
	public EmailSenderService emailSenderService(){
		return new EmailSenderServiceImpl();
	}
	
//	@Bean
//	public JavaMailSender javaMailSender(){
//		return new MailSe
//	}
}
