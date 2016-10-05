package com.realdolmen.chiro.batch;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberSyncerService {

    @Autowired
    private RegistrationParticipantRepository repository;

    @Autowired
    private ChiroUserAdapter adapter;

    @Scheduled(cron = "0/10 * * * * *")
    public void executeBookProcessingSchedule() {
        List<RegistrationParticipant> all = repository.findAll();

        if (all.size() == 0 ) {
            System.out.println("Couldn't find any registrations for participants");
        } else {
            for (RegistrationParticipant participant: all) {
                adapter.syncUser(participant);
            }
        }

    }
}
