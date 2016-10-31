package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class RegistrationVolunteerService {
    public final static Integer PRICE_IN_EUROCENTS = 6000;

	@Autowired
	private RegistrationVolunteerRepository repository;
	
	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

    @Autowired
    private ChiroUserAdapter adapter;

    @PreAuthorize("@RegistrationVolunteerServiceSecurity.hasPermissionToSaveVolunteer(#volunteer)")
    public RegistrationVolunteer save(RegistrationVolunteer volunteer){
        User chiroUser = adapter.getChiroUser(volunteer.getAdNumber());
        RegistrationVolunteer volunteerFromOurDB = repository.findByAdNumber(volunteer.getAdNumber());

        if(volunteerFromOurDB == null && chiroUser != null) {
            String stamnummer = chiroUser.getStamnummer();
            volunteer.setStamnumber(stamnummer);
            return repository.save(volunteer);
        } else if (volunteerFromOurDB != null && volunteerFromOurDB.getStatus().equals(Status.TO_BE_PAID) && chiroUser != null){
            volunteer.setId(volunteerFromOurDB.getId());
            return repository.save(volunteer);
        } else if (volunteerFromOurDB != null && (volunteerFromOurDB.getStatus().equals(Status.PAID))|| volunteerFromOurDB.getStatus().equals(Status.CONFIRMED)){
            return null;
        }
        return null;
    }
}
