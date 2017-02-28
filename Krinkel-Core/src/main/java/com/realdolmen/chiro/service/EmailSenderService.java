package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;

import java.util.concurrent.Future;

public interface EmailSenderService {
	Future<String> sendMail(RegistrationParticipant participant);

    Future<String> sendMail(RegistrationParticipant participant, boolean update);
}
