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

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.exception.EmailSenderServiceException;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;

@Service
@Async
public class EmailSenderServiceImpl implements EmailSenderService {
	private static final String EMAIL_FROM = "inschrijvingen@krinkel.be";
	private static final String EMAIL_SUBJECT = "Bevestiging inschrijving krinkel";

	@Autowired
	JavaMailSender mailSender;
	@Autowired
	private SpringTemplateEngine thymeleaf;

	@Override
	public Future<String> sendMail(RegistrationParticipant participant) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		Context ctx = new Context();
		ctx.setVariable("participant", participant);
		ctx.setVariable("isVolunteer", participant instanceof RegistrationVolunteer);

		String emailText = thymeleaf.process("email", ctx);
		ClassPathResource image = new ClassPathResource("/static/img/logo.png");

		try {
			helper.setText(emailText, true);
			helper.setFrom(EMAIL_FROM);
			helper.setTo(participant.getEmail());
			helper.setSubject(EMAIL_SUBJECT);
			helper.addInline("logo", image);
			mailSender.send(message);
		} catch (MessagingException me) {
			throw new EmailSenderServiceException("Something went wrong assembling email", me);
		}catch(Exception e){
			throw new EmailSenderServiceException("Something went wrong sending email", e);
		}
		

		return new AsyncResult<String>("ok");

	}

}
