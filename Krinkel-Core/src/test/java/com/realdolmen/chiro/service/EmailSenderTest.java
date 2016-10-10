package com.realdolmen.chiro.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

//import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.VolunteerFunction;
import com.realdolmen.chiro.domain.VolunteerFunction.Preset;
import com.realdolmen.chiro.exception.EmailSenderServiceException;
import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.Gender;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmailSenderTest {
	private static final String EMAIL_SUBJECT = "Bevestiging inschrijving krinkel";
	private static final String EMAIL_FROM = "inschrijvingen@krinkel.be";
	private static final String EMAIL_TO = "Mathew.Perry@someEmail.com";
	private static final String FIRST_NAME = "Mathew";
	private static final String LAST_NAME = "Perry";
	private static final String STREET="Alta Loma Rd";
	private static final String HOUSE_NUMBER="605";
	private static final String CITY="West Hollywood";
	private static final int POSTAL_CODE= 2000;
	private static final Gender MALE = Gender.MAN;
	

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private GreenMail smtpServer;

	private RegistrationParticipant registrationParticipant;

	private RegistrationVolunteer registrationVolunteer;

	@Before
	public void setup() {
		// smtpServer = new GreenMail(new ServerSetup(465, null, "smtp"));
		smtpServer.start();
		registrationParticipant = new RegistrationParticipant();
		registrationParticipant.setFirstName(FIRST_NAME);
		registrationParticipant.setLastName(LAST_NAME);
		registrationParticipant.setEmail(EMAIL_TO);
		registrationParticipant.setGender(MALE);
		Address address= new Address(STREET, HOUSE_NUMBER, POSTAL_CODE, CITY);
		registrationParticipant.setAddress(address);
		registrationVolunteer = new RegistrationVolunteer();
		registrationVolunteer.setFirstName(FIRST_NAME);
		registrationVolunteer.setLastName(LAST_NAME);
		registrationVolunteer.setEmail(EMAIL_TO);
		registrationVolunteer.setAddress(address);
		registrationVolunteer.setGender(MALE);
		registrationVolunteer.setFunction(new VolunteerFunction(Preset.KLINKER_EDITORIAL));
		
	}

	@After
	public void tearDown() {
		smtpServer.stop();
	}

	@Test
	public void testSend() throws MessagingException {
		emailSenderService.sendMail(registrationParticipant);
		emailSenderService.sendMail(registrationVolunteer);
		MimeMessage[] emails = smtpServer.getReceivedMessages();
		MimeMessage email1 = emails[0];
		MimeMessage email2 = emails[1];
		String body = GreenMailUtil.getBody(email1);
		System.out.println(body.trim());
		javax.mail.Address[] addressesMail1 = email1.getAllRecipients();
		javax.mail.Address[] addressesMail2 = email2.getAllRecipients();
		assertEquals(EMAIL_TO, addressesMail1[0].toString());
		assertEquals(EMAIL_TO, addressesMail2[0].toString());
		assertEquals(EMAIL_SUBJECT, emails[0].getSubject());
		System.out.println(emails[1].getFrom().toString());
		assertEquals(EMAIL_SUBJECT, emails[1].getSubject());
	}
	
	@Test(expected=EmailSenderServiceException.class)
	public void shouldThrowExceptionSendingEmail() throws MessagingException{
		RegistrationParticipant participant = new RegistrationParticipant();
		participant.setFirstName(FIRST_NAME);
		participant.setLastName(LAST_NAME);	
		participant.setAddress(new Address(STREET,HOUSE_NUMBER,POSTAL_CODE,CITY));
		emailSenderService.sendMail(participant);
	}
	
	/**
	 * exception evaluating Spring EL: ${street.address.street} generates Nullpointer in template
	 * 
	 * */
	@Test(expected=TemplateProcessingException.class)
	public void shouldThrowExceptionBecauseOfNullpointerInThymeLeafTemplate() throws MessagingException{
		RegistrationParticipant participant = new RegistrationParticipant();
		participant.setFirstName(FIRST_NAME);
		participant.setLastName(LAST_NAME);	
		emailSenderService.sendMail(participant);
	}

	@Test
	public void sendMailShouldReturnOk() throws MessagingException, InterruptedException, ExecutionException {
		assertEquals("ok", emailSenderService.sendMail(registrationParticipant).get());
	}
}
