package com.realdolmen.chiro.service;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmailSenderTest {
	private static final String EMAIL_SUBJECT = "Bevestiging inschrijving krinkel";
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private GreenMail smtpServer;
	
	private RegistrationParticipant registrationParticipant;
	
	private RegistrationVolunteer registrationVolunteer;
	
	@Before
	public void setup(){
		//smtpServer = new GreenMail(new ServerSetup(465, null, "smtp"));
		smtpServer.start();
		registrationParticipant = new RegistrationParticipant();
		registrationParticipant.setFirstName("Mathew");
		registrationParticipant.setLastName("Perry");
		registrationParticipant.setEmail("frank.claes@realdolmen.com");
	}
	
	@After
	public void tearDown(){
		smtpServer.stop();
	}

	@Test
	public void testSend() throws MessagingException {
		emailSenderService.sendMail(registrationParticipant);
	    MimeMessage[] emails = smtpServer.getReceivedMessages();
	    assertEquals(EMAIL_SUBJECT,emails[0].getSubject());
	}
	
	@Test
	public void sendMailShouldReturnOk() throws MessagingException, InterruptedException, ExecutionException {
		assertEquals("ok", emailSenderService.sendMail(registrationParticipant).get());
	}
	
	
}
