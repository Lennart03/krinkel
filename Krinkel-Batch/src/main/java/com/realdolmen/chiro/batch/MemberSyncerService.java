package com.realdolmen.chiro.batch;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberSyncerService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(MemberSyncerService.class);

    @Autowired
    private RegistrationParticipantRepository repository;

    @Autowired
    private ChiroUserAdapter adapter;

    //TODO: update the interval to be less frequent
    @Scheduled(cron = "0/10 * * * * *")
    public void syncUsersToChiroDB() {
        List<RegistrationParticipant> all = repository.findAll();

        logger.info("found " + all.size() + " participants");
        if (all.isEmpty()) {
            logger.info("Couldn't find any registrations for participants");
        } else {
            for (RegistrationParticipant participant: all) {
                adapter.syncUser(participant);
            }
        }
    }
}