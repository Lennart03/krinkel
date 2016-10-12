package com.realdolmen.chiro.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.service.EmailSenderService;
import com.realdolmen.chiro.service.EmailSenderServiceImpl;

@Configuration
@EnableJpaRepositories
@ComponentScan("com.realdolmen.chiro.service")
public class TestConfig {

//	@Bean
//	public EmailSenderService emailSenderService(){
//		return new EmailSenderServiceImpl();
//	}
	
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
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }
	
	@Bean
    public Database database() {
        return Database.H2;
    }
	
	@Bean
	//@Profile("test")
	public DataSource embeddedDataSource(){
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:registration_communication_data.sql").build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource,JpaVendorAdapter jpaVendorAdapter){
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan("com.realdolmen.chiro");
		return emfb;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter adaptor = new HibernateJpaVendorAdapter();
		adaptor.setDatabase(Database.H2);
		adaptor.setShowSql(true);
		adaptor.setGenerateDdl(true);
		return adaptor;
	}
	
	@Bean
	public ViewResolver viewResolver (SpringTemplateEngine templateEngine){
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		return viewResolver;
	}
	
	@Bean
	public TemplateEngine templateEngine(TemplateResolver templateResolver){
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(emailTemplateResolver());
		return templateEngine;
	}
	
	
	private TemplateResolver emailTemplateResolver() {
	    TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setSuffix("html");
	    templateResolver.setPrefix("/templates/");
	    templateResolver.setTemplateMode("HTML5");
	    templateResolver.setOrder(1);
	    return templateResolver;
	}
	
}
