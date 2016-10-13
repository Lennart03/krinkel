package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.ConfirmationLink;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.repository.ConfirmationLinkRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationLinkService {

    @Autowired
    private ConfirmationLinkRepository confirmationLinkRepository;

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    private String generateToken() {
        UUID token = UUID.randomUUID();
        return token.toString();
    }

    /**
     * Checks if given token matches with the registered token.
     * Updates status of the registration when correct.
     *
     * @param adNumber
     * @param token
     * @return True when successful, false otherwise.
     */
    public boolean checkToken(String adNumber, String token) {
        boolean isSuccess = false;
        ConfirmationLink confirmationLink = confirmationLinkRepository.findByAdNumber(adNumber);
        if(confirmationLink == null){
            return isSuccess;
        }

        if (confirmationLink.getToken().equals(token)) {
            RegistrationParticipant registration = registrationParticipantRepository.findByAdNumber(adNumber);

            if(registration.getStatus().equals(Status.PAID)){
                registration.setStatus(Status.CONFIRMED);
                registrationParticipantRepository.save(registration);
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    public ConfirmationLink createConfirmationLink(String adNumber) throws DuplicateEntryException {
        if(confirmationLinkRepository.findByAdNumber(adNumber) != null){
            throw new DuplicateEntryException("Confirmation link for this AD number already exists!");
        }
        return confirmationLinkRepository.save( new ConfirmationLink(adNumber, this.generateToken()));
    }
}