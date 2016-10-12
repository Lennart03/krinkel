package com.realdolmen.chiro.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.VolunteerFunction;
import com.realdolmen.chiro.domain.VolunteerFunction.Preset;
import com.realdolmen.chiro.exception.EmailSenderServiceException;
import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.CampGround;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.PostCamp;
import com.realdolmen.chiro.domain.PreCamp;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@ComponentScan("com.realdolmen.chiro.service")
@ContextConfiguration(classes=com.realdolmen.chiro.config.TestConfig.class)
 @Transactional
public class EmailSenderTest {
	private static final String EMAIL_SUBJECT = "Bevestiging inschrijving krinkel";
	private static final String EMAIL_FROM = "inschrijvingen@krinkel.be";
	private static final String EMAIL_TO = "Mathew.Perry@someEmail.com";
	private static final String AD_NUMBER_1 = "12345";
	private static final String AD_NUMBER_2 = "22345";
	private static final String FIRST_NAME = "Mathew";
	private static final String LAST_NAME = "Perry";
	private static final String STREET="Alta Loma Rd";
	private static final String HOUSE_NUMBER="605";
	private static final String CITY="West Hollywood";
	private static final int POSTAL_CODE= 2000;
	private static final Gender MALE = Gender.MAN;
	private static final CampGround CAMP_GROUND = CampGround.ANTWERPEN;
	private static final PreCamp PRE_CAMP_1 = new PreCamp();
	private static final PreCamp PRE_CAMP_2 = new PreCamp();
	private static final PostCamp POST_CAMP_1 = new PostCamp();
	private static final PostCamp POST_CAMP_2 = new PostCamp();
	private static final List<PreCamp>PRE_CAMP_LIST = new ArrayList<>();
	private static final List<PostCamp>POST_CAMP_LIST = new ArrayList<>();
	private static final VolunteerFunction VOL_FUNCTION1 = new VolunteerFunction();
	private static final VolunteerFunction VOL_FUNCTION2 = new VolunteerFunction();
	private static final String CUSTOM_FUNCTION = "Een ander jobke";
	
	

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
		registrationParticipant.setAdNumber(AD_NUMBER_1);
		registrationParticipant.setEmail(EMAIL_TO);
		registrationParticipant.setGender(MALE);
		Address address= new Address(STREET, HOUSE_NUMBER, POSTAL_CODE, CITY);
		registrationParticipant.setAddress(address);
		registrationVolunteer = new RegistrationVolunteer();
		registrationVolunteer.setFirstName(FIRST_NAME);
		registrationVolunteer.setLastName(LAST_NAME);
		registrationVolunteer.setAdNumber(AD_NUMBER_2);
		registrationVolunteer.setEmail(EMAIL_TO);
		registrationVolunteer.setAddress(address);
		registrationVolunteer.setGender(MALE);
		registrationVolunteer.setFunction(new VolunteerFunction(Preset.KLINKER_EDITORIAL));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.MONTH,7);//zero-based!!!
		calendar.set(Calendar.DAY_OF_MONTH,21);
		PRE_CAMP_1.setDate(calendar.getTime());
		calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.MONTH,7);//zero-based!!!
		calendar.set(Calendar.DAY_OF_MONTH,22);
		PRE_CAMP_2.setDate(calendar.getTime());
		calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.MONTH,8);//zero-based!!!
		calendar.set(Calendar.DAY_OF_MONTH,1);
		POST_CAMP_1.setDate(calendar.getTime());
		calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.MONTH,8);//zero-based!!!
		calendar.set(Calendar.DAY_OF_MONTH,2);
		POST_CAMP_2.setDate(calendar.getTime());
		PRE_CAMP_LIST.add(PRE_CAMP_1);
		PRE_CAMP_LIST.add(PRE_CAMP_2);
		POST_CAMP_LIST.add(POST_CAMP_1);
		POST_CAMP_LIST.add(POST_CAMP_2);
		registrationVolunteer.setCampGround(CAMP_GROUND);
//		registrationVolunteer.setPreCampList(PRE_CAMP_LIST);
//		registrationVolunteer.setPostCampList(POST_CAMP_LIST);
		VOL_FUNCTION1.setPreset(Preset.CAMPGROUND);
		VOL_FUNCTION2.setOther(CUSTOM_FUNCTION);
		registrationVolunteer.setFunction(VOL_FUNCTION1);
		
		
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
		String body = GreenMailUtil.getBody(email2);
		System.out.println(body.trim());
		javax.mail.Address[] addressesMail1 = email1.getAllRecipients();
		javax.mail.Address[] addressesMail2 = email2.getAllRecipients();
		assertEquals(EMAIL_TO, addressesMail1[0].toString());
		assertEquals(EMAIL_TO, addressesMail2[0].toString());
		assertEquals(EMAIL_SUBJECT, emails[0].getSubject());
		System.out.println(emails[1].getFrom().toString());
		assertEquals(EMAIL_SUBJECT, emails[1].getSubject());
	}
	
	@Test
	public void shouldContainCustomFunction() throws MessagingException {
		registrationVolunteer.setFunction(VOL_FUNCTION2);
		emailSenderService.sendMail(registrationVolunteer);
		MimeMessage[] emails = smtpServer.getReceivedMessages();
		MimeMessage email1 = emails[0];
		String body = GreenMailUtil.getBody(email1);
		assertTrue(body.contains(CUSTOM_FUNCTION));
	}
	@Test
	public void shouldNotContainPresetFunction() throws MessagingException {
		registrationVolunteer.setFunction(VOL_FUNCTION2);
		emailSenderService.sendMail(registrationVolunteer);
		MimeMessage[] emails = smtpServer.getReceivedMessages();
		MimeMessage email1 = emails[0];
		String body = GreenMailUtil.getBody(email1);
		assertFalse(body.contains(VOL_FUNCTION1.getPreset().getDescription()));
	}	
	
	
	@Test
	public void shouldContainPresetFunction() throws MessagingException {
		registrationVolunteer.setFunction(VOL_FUNCTION1);
		emailSenderService.sendMail(registrationVolunteer);
		MimeMessage[] emails = smtpServer.getReceivedMessages();
		MimeMessage email1 = emails[0];
		String body = GreenMailUtil.getBody(email1);
		assertTrue(body.contains(VOL_FUNCTION1.getPreset().getDescription()));
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
