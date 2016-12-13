package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class RegistrationVolunteerService {

    @Value("${price.volunteer}")
    private Integer PRICE_IN_EUROCENTS;

    @Autowired
    private RegistrationVolunteerRepository registrationVolunteerRepository;

    @Autowired
    private RegistrationCommunicationRepository registrationCommunicationRepository;

    private Logger logger = LoggerFactory.getLogger(RegistrationParticipantService.class);

    @Autowired
    private UserService userService;

    @PreAuthorize("@RegistrationVolunteerServiceSecurity.hasPermissionToSaveVolunteer(#volunteer)")
    public RegistrationVolunteer save(RegistrationVolunteer volunteer) {
        RegistrationVolunteer volunteerFromOurDB = registrationVolunteerRepository.findByAdNumber(volunteer.getAdNumber());
        volunteer.setRegisteredBy(volunteer.getAdNumber());

        if (volunteerFromOurDB == null) {
            userService.updateCurrentUserRegisteredStatus();
            return registrationVolunteerRepository.save(volunteer);
        } else if (volunteerFromOurDB != null && volunteerFromOurDB.getStatus().equals(Status.TO_BE_PAID)) {
            volunteer.setId(volunteerFromOurDB.getId());
            return registrationVolunteerRepository.save(volunteer);
        } else if (volunteerFromOurDB != null && (volunteerFromOurDB.getStatus().equals(Status.PAID)) || volunteerFromOurDB.getStatus().equals(Status.CONFIRMED)) {
            return null;
        }
        return null;
    }


    public void markAsPayed(RegistrationVolunteer volunteer) {
        if (volunteer != null) {
            volunteer.setStatus(Status.PAID);

            createRegistrationCommunication(volunteer);
            this.save(volunteer);
        }
    }

    public void createRegistrationCommunication(RegistrationVolunteer volunteer) {
        RegistrationCommunication registrationCommunication = new RegistrationCommunication();
        registrationCommunication.setStatus(SendStatus.WAITING);
        registrationCommunication.setCommunicationAttempt(0);
        registrationCommunication.setAdNumber(volunteer.getAdNumber());
        if (registrationCommunicationRepository.findByAdNumber(volunteer.getAdNumber()) == null) {
            logger.info("registering communication to participant/volunteer with ad-number: "
                    + volunteer.getAdNumber() + " with status: " + registrationCommunication.getStatus());
            registrationCommunicationRepository.save(registrationCommunication);
        }
    }

    public Integer getPRICE_IN_EUROCENTS() {
        return PRICE_IN_EUROCENTS;
    }
}
