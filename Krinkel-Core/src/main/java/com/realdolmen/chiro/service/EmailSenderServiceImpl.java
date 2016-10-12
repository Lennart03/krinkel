package com.realdolmen.chiro.service;

import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.SendStatus;
import com.realdolmen.chiro.exception.EmailSenderServiceException;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;

@Service
@Async
public class EmailSenderServiceImpl implements EmailSenderService {
	private static final String EMAIL_FROM = "inschrijvingen@krinkel.be";
	private static final String EMAIL_SUBJECT = "Bevestiging inschrijving krinkel";
	private RegistrationCommunication registrationCommunication;

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine thymeleaf;

	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

	@Override
	public Future<String> sendMail(RegistrationParticipant participant) {

		MimeMessage message = mailSender.createMimeMessage();

		Context ctx = new Context();
		ctx.setVariable("participant", participant);
		ctx.setVariable("isVolunteer", participant instanceof RegistrationVolunteer);

		String emailText = thymeleaf.process("email", ctx);
		ClassPathResource image = new ClassPathResource("/static/img/logo.png");
		
		registrationCommunication = new RegistrationCommunication();
		registrationCommunication.setAdNumber(participant.getAdNumber());
		registrationCommunication.setCommunicationAttempt(0);
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setText(emailText, true);
			helper.setFrom(EMAIL_FROM);
			helper.setTo(participant.getEmail());
			helper.setSubject(EMAIL_SUBJECT);
			helper.addInline("logo", image);

			mailSender.send(message);
			
			registrationCommunication.setStatus(SendStatus.SENT);
			registrationCommunicationRepository.save(registrationCommunication);
		} catch (Exception e) {
			registrationCommunication.setStatus(SendStatus.FAILED);
			registrationCommunicationRepository.save(registrationCommunication);
		}

		return new AsyncResult<String>("ok");

	}

}
