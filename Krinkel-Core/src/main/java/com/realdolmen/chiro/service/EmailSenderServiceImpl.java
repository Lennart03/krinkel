package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.exception.DuplicateEntryException;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Future;

@Service
@Async
public class EmailSenderServiceImpl implements EmailSenderService {

    @Value("${mail.from}")
    private String EMAIL_FROM;

    @Value("${mail.subject}")
    private String EMAIL_SUBJECT;
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
        return this.sendMail(participant, false);
    }


    @Override
    public Future<String> sendMail(RegistrationParticipant participant, boolean update) {

        MimeMessage message = mailSender.createMimeMessage();
        Context ctx = new Context();

        participant = fillInEmptyFields(participant);

        ctx.setVariable("participant", participant);
        ctx.setVariable("isVolunteer", participant instanceof RegistrationVolunteer);
        String url = "";

        //Create confirmation URL in case the participant is registered by someone else
        if (participant.isRegisteredByOther() || (update && participant.getStatus().equals(Status.PAID))) {


            ConfirmationLink confirmationLink = confirmationLinkService.findByAdNumber(participant.getAdNumber());
            if(confirmationLink != null){
                url = confirmationLinkService.generateURLFromConfirmationLink(confirmationLink);
                logger.info("Confirmation link retrieved: " + url);
            } else {
                try {
                    confirmationLink = confirmationLinkService.createConfirmationLink(participant.getAdNumber());
                    url = confirmationLinkService.generateURLFromConfirmationLink(confirmationLink);
                    logger.info("New confirmation link created: " + url);
                } catch (DuplicateEntryException e) {
                    logger.warn("Attempted to make a duplicate confirmation link");
                }
            }
        }

        ctx.setVariable("isMailToSubscriber", false);
        ctx.setVariable("confirmationLink", url);
        ctx.setVariable("status", participant.getStatus().toString());
        System.out.println("STATUS PARTICIPANT: " + participant.getStatus().toString());
        ctx.setVariable("updateByAdmin", update);

        String emailText = thymeleaf.process("email", ctx);
        ClassPathResource image = new ClassPathResource("/static/img/logo.png");

        registrationCommunication = registrationCommunicationRepository.findByAdNumber(participant.getAdNumber());
        registrationCommunication.setCommunicationAttempt(registrationCommunication.getCommunicationAttempt() + 1);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String emailTo = participant.getEmail();
            helper = setHelper(helper, emailText, image, emailTo);

            logger.info("trying to send confirmation email to: " + participant.getEmail());
            mailSender.send(message);

            //check for empty string is REQUIRED, empty strings are not null. if this code fails, the user will be spammed because the service keeps trying
            if (!update && !participant.getAdNumber().equals(participant.getRegisteredBy()) && participant.isRegisteredByOther() && participant.getEmailSubscriber() != null && !participant.getEmailSubscriber().trim().isEmpty()) {

                ctx.setVariable("isMailToSubscriber", true);

                emailText = thymeleaf.process("email", ctx);

                logger.info("and trying to email to: " + participant.getEmailSubscriber());
                emailTo = participant.getEmailSubscriber();

                helper = new MimeMessageHelper(message, true);
                helper = setHelper(helper, emailText, image, emailTo);

                mailSender.send(message);
            }

            registrationCommunication.setStatus(SendStatus.SENT);
            registrationCommunicationRepository.save(registrationCommunication);
            logger.info("sending confirmation email to: " + participant.getEmail() + " succeeded");
            if (participant.getEmailSubscriber() != null && !participant.getEmailSubscriber().trim().isEmpty()) {
                logger.info("sending email to: " + participant.getEmailSubscriber() + " succeeded");
            }
            return new AsyncResult<String>("ok");
        }catch (AddressException ae){
            logger.info("One of the provided email addresses were not valid, setting mail to cancelled to prevent spam.");
            registrationCommunication.setStatus(SendStatus.CANCELLED);
            registrationCommunicationRepository.save(registrationCommunication);
            return new AsyncResult<String>("cancelled");
        }
        catch (Exception e) {
            registrationCommunication.setStatus(SendStatus.FAILED);
            logger.error("sending mail failed", e);
            logger.info("sending confirmation email to: " + participant.getEmail() + " failed");
            registrationCommunicationRepository.save(registrationCommunication);
            return new AsyncResult<String>("notOk");
        }
    }

    private RegistrationParticipant fillInEmptyFields(RegistrationParticipant participant) {
        if (participant.getMedicalRemarks() == null) {
            participant.setMedicalRemarks("geen");
        }
        if (participant.getRemarksFood() == null) {
            participant.setRemarksFood("geen");
        }
        if (participant.getRemarks() == null) {
            participant.setRemarks("geen");
        }

        return participant;
    }

    public MimeMessageHelper setHelper(MimeMessageHelper helper, String emailText, ClassPathResource image, String emailTo) throws MessagingException {
        helper.setText(emailText, true);
        helper.setFrom(EMAIL_FROM);
        helper.setTo(emailTo);
        helper.setSubject(EMAIL_SUBJECT);
        helper.addInline("logo", image);
        return helper;
    }
}
