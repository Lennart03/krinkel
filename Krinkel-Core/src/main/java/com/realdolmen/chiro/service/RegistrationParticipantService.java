package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationCommunication;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SendStatus;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationParticipantService {

    @Autowired
    private RegistrationParticipantRepository repository;
    
	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

    public RegistrationParticipant save(RegistrationParticipant registration) {
        if(repository.findByAdNumber(registration.getAdNumber()) == null) {
        	RegistrationCommunication regCom = new RegistrationCommunication();
			regCom.setAdNumber(registration.getAdNumber());
			regCom.setCommunicationAttempt(0);
			regCom.setStatus(SendStatus.WAITING);
        	registrationCommunicationRepository.save(regCom);
        	return repository.save(registration);
        }
        return null;
    }

    public List<RegistrationParticipant> findParticipantsByGroup(String stamNumber){
        List<RegistrationParticipant> participants = repository.findParticipantsByGroup(stamNumber);
        List<RegistrationParticipant> results = new ArrayList<>();
        for (RegistrationParticipant participant: participants){
            if (!(participant instanceof RegistrationVolunteer)){
                results.add(participant);
            }
        }
        return results;
    }

    public List<RegistrationVolunteer> findVolunteersByGroup(String stamNumber){
        List<RegistrationParticipant> participants = repository.findParticipantsByGroup(stamNumber);
        List<RegistrationVolunteer> results = new ArrayList<>();
        for (RegistrationParticipant participant: participants){
            if (participant instanceof RegistrationVolunteer){
                results.add((RegistrationVolunteer)participant);
            }
        }
        return results;
    }
}
