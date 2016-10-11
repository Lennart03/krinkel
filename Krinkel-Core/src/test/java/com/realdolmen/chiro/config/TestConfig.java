package com.realdolmen.chiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.service.EmailSenderService;
import com.realdolmen.chiro.service.EmailSenderServiceImpl;
import com.realdolmen.chiro.service.RegistrationCommunicationService;
import com.realdolmen.chiro.service.RegistrationCommunicationServiceImpl;

@Configuration
//@Profile("test")
//@ComponentScan("com.realdolmen.chiro.repository")
@EnableJpaRepositories
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
	
	@Bean
	public RegistrationCommunicationService registrationCommunicationService(){
		return new RegistrationCommunicationServiceImpl();
	}
	
	
	
	
}
