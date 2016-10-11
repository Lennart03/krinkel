package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationParticipantService {
    public final static Integer PRICE_IN_EUROCENTS = 11000;

    @Autowired
    private RegistrationParticipantRepository repository;

    @Autowired
    private MultiSafePayService mspService;


    public RegistrationParticipant save(RegistrationParticipant registration) {
        if (repository.findByAdNumber(registration.getAdNumber()) == null) {
            return repository.save(registration);
        }
        return null;
    }

    public void updatePaymentStatus(String testOrderId) {
        RegistrationParticipant participant = repository.findByAdNumber(testOrderId);
        if (participant != null) {

            if (mspService.orderIsPaid(testOrderId)) {
                participant.setStatus(Status.PAID);
                repository.save(participant);
            }
        }
    }
}
