package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationParticipantService {


    @Value("${price.participant}")
    private Integer PRICE_IN_EUROCENTS;


    private Logger logger = LoggerFactory.getLogger(RegistrationParticipantService.class);

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    private RegistrationCommunicationRepository registrationCommunicationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MultiSafePayService multiSafePayService;

    @PreAuthorize("@RegistrationParticipantServiceSecurity.hasPermissionToSaveParticipant(#participant)")
    public RegistrationParticipant save(RegistrationParticipant participant) {
        RegistrationParticipant participantFromOurDB = registrationParticipantRepository
                .findByAdNumber(participant.getAdNumber());

        if (participantFromOurDB == null) {
            User currentUser = userService.getCurrentUser();
            participant.setRegisteredBy(currentUser.getAdNumber());
            if (participant.getAdNumber().equals(participant.getRegisteredBy())) {
                userService.updateCurrentUserRegisteredStatus();
            }
            return registrationParticipantRepository.save(participant);
        } else if (participantFromOurDB != null && participantFromOurDB.getStatus().equals(Status.TO_BE_PAID)) {
            participant.setId(participantFromOurDB.getId());
            User currentUser = userService.getCurrentUser();
            participantFromOurDB.setRegisteredBy(currentUser.getAdNumber());
            return registrationParticipantRepository.save(participant);
        } else if (participantFromOurDB != null && (participantFromOurDB.getStatus().equals(Status.PAID)) || participantFromOurDB.getStatus().equals(Status.CONFIRMED)) {
            return null;
        }
        return null;
    }

    public void updatePaymentStatus(String testOrderId) {
        logger.info("in updatePaymentStatus()");
        String[] split = testOrderId.split("-");
        String adNumber = split[0];
        RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(adNumber);
        if (participant != null) {
            logger.info("found participant " + participant.getAdNumber());

            if (multiSafePayService.orderIsPaid(testOrderId)) {
                if (participant.getAdNumber().equals(participant.getRegisteredBy())) {
                    participant.setStatus(Status.CONFIRMED);
                    userService.updateCurrentUserPayStatus();
                } else {
                    participant.setStatus(Status.PAID);
                }
                logger.info("participant " + participant.getAdNumber() + " on status '" + participant.getStatus().toString() + "'");

                RegistrationCommunication registrationCommunication = new RegistrationCommunication();
                registrationCommunication.setStatus(SendStatus.WAITING);
                registrationCommunication.setCommunicationAttempt(0);
                registrationCommunication.setAdNumber(participant.getAdNumber());
                if (registrationCommunicationRepository.findByAdNumber(participant.getAdNumber()) == null) {
                    logger.info("registering communication to participant/volunteer with ad-number: "
                            + participant.getAdNumber() + " with status: " + registrationCommunication.getStatus());
                    registrationCommunicationRepository.save(registrationCommunication);
                }
                registrationParticipantRepository.save(participant);
            }
        }
    }

    /**
     * Return a list of all participants within the specified group which have a
     * registration status of Confirmed or Paid.
     * <p>
     * Only returns pure participants. Volunteers are ignored.
     *
     * @param stamNumber Identifier of the group.
     */
    public List<RegistrationParticipant> findParticipantsByGroup(String stamNumber) {
        List<RegistrationParticipant> participants = registrationParticipantRepository
                .findParticipantsByGroupWithStatusConfirmedOrPaid(stamNumber);
        List<RegistrationParticipant> results = new ArrayList<>();
        for (RegistrationParticipant participant : participants) {
            if (!(participant instanceof RegistrationVolunteer)) {
                results.add(participant);
            }
        }
        return results;
    }

    /**
     * Return of all volunteers within the specified group which have a
     * registration status of Confirmed or Paid.
     *
     * @param stamNumber
     */
    public List<RegistrationVolunteer> findVolunteersByGroup(String stamNumber) {
        List<RegistrationParticipant> participants = registrationParticipantRepository
                .findParticipantsByGroupWithStatusConfirmedOrPaid(stamNumber);
        List<RegistrationVolunteer> results = new ArrayList<>();
        for (RegistrationParticipant participant : participants) {
            if (participant instanceof RegistrationVolunteer) {
                results.add((RegistrationVolunteer) participant);
            }
        }
        return results;
    }

    public List<RegistrationParticipant> getSyncReadyParticipants() {
        return registrationParticipantRepository.findRegistrationParticipantsWithStatusUnsyncedAndConfirmed();
    }

    public void setUserToSynced(String adnumber) {
        RegistrationParticipant user = registrationParticipantRepository.findByAdNumber(adnumber);
        if (user.getStatus() == Status.CONFIRMED) {
            user.setSyncStatus(SyncStatus.SYNCED);
            registrationParticipantRepository.save(user);
        }
    }

    public Integer getPRICE_IN_EUROCENTS() {
        return PRICE_IN_EUROCENTS;
    }


    public RegistrationParticipant cancel(Long participantId) {
        RegistrationParticipant participant = registrationParticipantRepository.findOne(participantId);
        participant.setStatus(Status.CANCELLED);

        // If participant is a buddy, remove the language records for statistics and DB size reasons.
        if (participant.isBuddy()) {
            registrationParticipantRepository.removeBuddyLanguageRecordsAfterCancellation(participantId);
            participant.setBuddy(false);
        }

        // If participant takes part in Pre Camp, remove the day records for statistics and DB size reasons.
        if(registrationParticipantRepository.countPreCampRecordsAfterCancellation(participantId) > 0 ){
            registrationParticipantRepository.removePreCampRecordsAfterCancellation(participantId);
        }

        // If participant takes part in Post Camp, remove the day records for statistics and DB size reasons.
        if(registrationParticipantRepository.countPostCampRecordsAfterCancellation(participantId) > 0) {
            registrationParticipantRepository.removePostCampRecordsAfterCancellation(participantId);
        }

        return registrationParticipantRepository.save(participant);

    }
}
