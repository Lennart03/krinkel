package com.realdolmen.chiro.batch;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberSyncerService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(MemberSyncerService.class);

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @Autowired
    private ChiroUserAdapter chiroUserAdapter;
    
    /**
     * Periodically sync all people in our database that have the status CONFIRMED to the chiro's DB.
     * This done through a batch job so that when a sync fails, it can be done again. On succes, the user will also
     * get the status SYNCED.
     */
    @Scheduled(cron = "${sync.cron.timer}")
    public void syncUsersToChiroDB() {
        List<RegistrationParticipant> all = registrationParticipantService.getSyncReadyParticipants();

        if (all.isEmpty()) {
//            logger.info("Couldn't find any registrations for participants");
        } else {
            logger.info("found " + all.size() + " participants");

            for (RegistrationParticipant participant: all) {
                logger.info("Syncing participant with id: " + participant.getId() + " and ad number " + participant.getAdNumber());

                try {
                    chiroUserAdapter.syncUser(participant);
                    registrationParticipantService.setUserToSynced(participant.getAdNumber());
                    logger.info("succesfully synced user with id " + participant.getId() + " and ad number " + participant.getAdNumber());
                } catch (Exception e) {
                    logger.info("Failed to sync participant with " + participant.getId() + " and ad number " + participant.getAdNumber());
                }
            }
        }
    }
}