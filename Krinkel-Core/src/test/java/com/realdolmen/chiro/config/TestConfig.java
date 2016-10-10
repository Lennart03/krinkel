package com.realdolmen.chiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.realdolmen.chiro.service.EmailSenderService;
import com.realdolmen.chiro.service.EmailSenderServiceImpl;

@Configuration
public class TestConfig {

	@Bean
	public EmailSenderService emailSenderService(){
		return new EmailSenderServiceImpl();
	}
	
	@Bean
	public GreenMail greenMail(){
		return new GreenMail(new ServerSetup(465, "localhost", "smtp"));
	}
	
	@Bean
	public JavaMailSender javaMailSender(){
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("localhost");
		javaMailSenderImpl.setPort(465);
		javaMailSenderImpl.setProtocol("smtp");
		return javaMailSenderImpl;
	}
}
