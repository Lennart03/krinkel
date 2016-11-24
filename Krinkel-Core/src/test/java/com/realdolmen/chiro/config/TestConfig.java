package com.realdolmen.chiro.config;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@ActiveProfiles("test")
@ComponentScan("com.realdolmen.chiro")
@PropertySource("classpath:/application-test.properties")
public class TestConfig {

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
	public ViewResolver viewResolver (SpringTemplateEngine templateEngine){
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		return viewResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(TemplateResolver templateResolver){
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	@Bean
	public ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setPrefix("templates/");
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode("HTML5");        
	    templateResolver.setCacheable(false);
	    return templateResolver;
	}
}
