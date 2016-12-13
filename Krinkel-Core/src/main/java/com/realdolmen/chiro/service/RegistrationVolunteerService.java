package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;

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

            //TODO deze | lijn uit commentaar halen als met brentc zijn branch is gemerged
            //          v
            //registrationVolunteerRepository.createRegistrationCommunication(participant);
            this.save(volunteer);
        }
    }

    public Integer getPRICE_IN_EUROCENTS() {
        return PRICE_IN_EUROCENTS;
    }
}
