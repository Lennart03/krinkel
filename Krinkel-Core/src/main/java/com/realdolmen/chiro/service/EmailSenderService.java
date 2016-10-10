package com.realdolmen.chiro.service;

import java.util.concurrent.Future;

import javax.mail.MessagingException;
import org.springframework.stereotype.Service;

import com.realdolmen.chiro.domain.RegistrationParticipant;


@Service
public interface EmailSenderService {
	Future<String> sendMail(RegistrationParticipant participant) throws MessagingException;
}
