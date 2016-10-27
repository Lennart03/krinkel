package com.realdolmen.chiro.service;

import java.util.concurrent.Future;

import javax.mail.internet.MimeMessage;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.exception.DuplicateEntryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;

@Service
@Async
public class EmailSenderServiceImpl implements EmailSenderService {
    private static final String EMAIL_FROM = "inschrijvingen@krinkel.be";
    private static final String EMAIL_SUBJECT = "Bevestiging inschrijving krinkel";
    private RegistrationCommunication registrationCommunication;

    private Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine thymeleaf;

    @Autowired
    private RegistrationCommunicationRepository registrationCommunicationRepository;

    @Autowired
    private ConfirmationLinkService confirmationLinkService;

    @Override
    public Future<String> sendMail(RegistrationParticipant participant) {
        MimeMessage message = mailSender.createMimeMessage();
        Context ctx = new Context();

        participant = fillInEmptyFields(participant);

        ctx.setVariable("participant", participant);
        ctx.setVariable("isVolunteer", participant instanceof RegistrationVolunteer);
        String url = "";

        if (participant.isRegisteredByOther()) {
            try {
                ConfirmationLink confirmationLink = confirmationLinkService.createConfirmationLink(participant.getAdNumber());
                url = confirmationLinkService.generateURLFromConfirmationLink(confirmationLink);
            } catch (DuplicateEntryException e) {
                logger.warn("Attempted to make a duplicate confirmation link");
            }
        }
        ctx.setVariable("confirmationLink", url);

        String emailText = thymeleaf.process("email", ctx);
        ClassPathResource image = new ClassPathResource("/static/img/logo.png");

        registrationCommunication = registrationCommunicationRepository.findByAdNumber(participant.getAdNumber());
        registrationCommunication.setCommunicationAttempt(registrationCommunication.getCommunicationAttempt() + 1);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setText(emailText, true);
            helper.setFrom(EMAIL_FROM);
            helper.setTo(participant.getEmail());
            helper.setSubject(EMAIL_SUBJECT);
            helper.addInline("logo", image);
            logger.info("trying to send confirmation email to: " + participant.getEmail());
            mailSender.send(message);

            registrationCommunication.setStatus(SendStatus.SENT);
            registrationCommunicationRepository.save(registrationCommunication);
            logger.info("sending confirmation email to: " + participant.getEmail() + " succeeded");
            return new AsyncResult<String>("ok");
        } catch (Exception e) {
            registrationCommunication.setStatus(SendStatus.FAILED);
            logger.error("sending mail failed", e);
            logger.info("sending confirmation email to: " + participant.getEmail() + " failed");
            registrationCommunicationRepository.save(registrationCommunication);
            return new AsyncResult<String>("notOk");
        }
    }

    private RegistrationParticipant fillInEmptyFields(RegistrationParticipant participant) {
        if (participant.getMedicalRemarks() == null)
        {
            participant.setMedicalRemarks("geen");
        }
        if (participant.getRemarksFood() == null)
        {
            participant.setRemarksFood("geen");
        }
        if (participant.getRemarks() == null)
        {
            participant.setRemarks("geen");
        }

        return participant;
    }

}
