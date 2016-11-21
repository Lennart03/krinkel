package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.User;
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
    private RegistrationVolunteerRepository repository;

    @Autowired
    private UserService userService;

    @PreAuthorize("@RegistrationVolunteerServiceSecurity.hasPermissionToSaveVolunteer(#volunteer)")
    public RegistrationVolunteer save(RegistrationVolunteer volunteer) {
        RegistrationVolunteer volunteerFromOurDB = repository.findByAdNumber(volunteer.getAdNumber());
        volunteer.setRegisteredBy(volunteer.getAdNumber());

        if (volunteerFromOurDB == null) {
            userService.updateCurrentUserRegisteredStatus();
            return repository.save(volunteer);
        } else if (volunteerFromOurDB != null && volunteerFromOurDB.getStatus().equals(Status.TO_BE_PAID)) {
            volunteer.setId(volunteerFromOurDB.getId());
            return repository.save(volunteer);
        } else if (volunteerFromOurDB != null && (volunteerFromOurDB.getStatus().equals(Status.PAID)) || volunteerFromOurDB.getStatus().equals(Status.CONFIRMED)) {
            return null;
        }
        return null;
    }

    public Integer getPRICE_IN_EUROCENTS() {
        return PRICE_IN_EUROCENTS;
    }
}
