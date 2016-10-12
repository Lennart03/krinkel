package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationParticipantService {
    public final static Integer PRICE_IN_EUROCENTS = 11000;

    @Autowired
    private RegistrationParticipantRepository repository;

    @Autowired
    private MultiSafePayService mspService;

    @Autowired
    private ChiroUserAdapter adapter;


    public RegistrationParticipant save(RegistrationParticipant registration) {
        if (repository.findByAdNumber(registration.getAdNumber()) == null) {
            String stamnummer = adapter.getChiroUser(registration.getAdNumber()).getStamnummer();
            registration.setStamnumber(stamnummer);
            return repository.save(registration);
        }
        return null;
    }

    public void updatePaymentStatus(String testOrderId) {
        String[] split = testOrderId.split("-");
        testOrderId = split[0];

        RegistrationParticipant participant = repository.findByAdNumber(testOrderId);
        if (participant != null) {

            if (mspService.orderIsPaid(testOrderId)) {
                participant.setStatus(Status.PAID);
                repository.save(participant);
            }
        }
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
