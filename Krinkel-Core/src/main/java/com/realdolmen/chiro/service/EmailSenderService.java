package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.ConfirmationLink;
import com.realdolmen.chiro.domain.RegistrationParticipant;

import java.util.concurrent.Future;

public interface EmailSenderService {
	Future<String> sendMail(RegistrationParticipant participant);

	Future<String> resendMail(RegistrationParticipant participant, ConfirmationLink confirmationLink);
}
