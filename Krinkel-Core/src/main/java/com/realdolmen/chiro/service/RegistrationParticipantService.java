package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.exception.NoParticipantFoundException;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
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

            validateStamNumber(participant);
            return registrationParticipantRepository.save(participant);
        } else if (participantFromOurDB.getStatus().equals(Status.TO_BE_PAID)) {

            participant.setId(participantFromOurDB.getId());
            User currentUser = userService.getCurrentUser();
            participantFromOurDB.setRegisteredBy(currentUser.getAdNumber());

            validateStamNumber(participant);
            return registrationParticipantRepository.save(participant);

        } else if (participantFromOurDB.getStatus().equals(Status.PAID) || participantFromOurDB.getStatus().equals(Status.CONFIRMED)) {
            return null;
        }
        return null;
    }


    public void updatePaymentStatus(String orderId) {
        logger.info("in updatePaymentStatus()");
        String[] split = orderId.split("-");
        String adNumber = split[0];
        RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(adNumber);
        if (participant != null) {
            logger.info("found participant " + participant.getAdNumber());

            if (multiSafePayService.orderIsPaid(orderId)) {
                if (participant.getAdNumber().equals(participant.getRegisteredBy())) {
                    participant.setStatus(Status.CONFIRMED);
                    userService.updateCurrentUserPayStatus();
                } else {
                    participant.setStatus(Status.PAID);
                }
                logger.info("participant " + participant.getAdNumber() + " on status '" + participant.getStatus().toString() + "'");
                createRegistrationCommunication(participant);
                registrationParticipantRepository.save(participant);
            }
        }
    }

    public void markAsPayed(RegistrationParticipant participant) {
        if (participant != null) {
            participant.setStatus(Status.PAID);

            createRegistrationCommunication(participant);
            this.save(participant);
        }
    }


    public void createRegistrationCommunication(RegistrationParticipant participant) {
        RegistrationCommunication registrationCommunication = new RegistrationCommunication();
        registrationCommunication.setStatus(SendStatus.WAITING);
        registrationCommunication.setCommunicationAttempt(0);
        registrationCommunication.setAdNumber(participant.getAdNumber());
        if (registrationCommunicationRepository.findByAdNumber(participant.getAdNumber()) == null) {
            logger.info("registering communication to participant/volunteer with ad-number: "
                    + participant.getAdNumber() + " with status: " + registrationCommunication.getStatus());
            registrationCommunicationRepository.save(registrationCommunication);
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
                .findParticipantsByGroupWithStatusConfirmedOrToBePaidOrPaid(stamNumber);
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
                .findParticipantsByGroupWithStatusConfirmedOrToBePaidOrPaid(stamNumber);
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
        participant.updateLastChange();
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

    public boolean isUserAlreadyRegistered(String adNumber) {
        RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(adNumber);
        if (participant == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public RegistrationParticipant updatePaymentStatusAdmin(Long participantId, String paymentStatus) {
        RegistrationParticipant participant = registrationParticipantRepository.findOne(participantId);
        participant.updateLastChange();
        if (Status.valueOf(paymentStatus) == Status.TO_BE_PAID) {
            participant.setStatus(Status.TO_BE_PAID);
        } else if (Status.valueOf(paymentStatus) == Status.PAID) {
            //see if we can replace this with a call to markAsPayed, in order to also set the flag to send a mail.
            participant.setStatus(Status.PAID);
        } else if (Status.valueOf(paymentStatus) == Status.CONFIRMED) {
            participant.setStatus(Status.CONFIRMED);
        }
        return registrationParticipantRepository.save(participant);
    }

    /*
    * retrieve a user using his/her ADNumber
    * */
    public RegistrationParticipant getRegistrationParticipantByAdNumber(String adNumber) throws NoParticipantFoundException {
        RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(adNumber);
        if (participant == null) {
            throw new NoParticipantFoundException("No Member found with Ad number ");
        }
        return participant;
    }

    /**
     * Changes the stamnumber to OTHERS if it's not known by the system, and sets the original stamno for export.
     * we do this as a band-aid, because the app doesn't inherently support linking unknown stam numbers
     * @param participant
     */
    private void validateStamNumber(RegistrationParticipant participant){ //fixme
        Verbond verbond = Verbond.getVerbondFromStamNumber(participant.getStamnumber());
        System.out.println("Checking number for AD: "+ participant.getAdNumber());
        System.out.println("verbond = " + verbond);
        //TODO: check if stam number is part of list of national => change stamNumber to "NAT" or "INT" if "5DI"
        if(verbond == Verbond.OTHERS && !participant.getStamnumber().equals("OTHERS")) {
            System.out.println("Verbond unknown, changing to others...");
            participant.setOriginalStamNumber(participant.getStamnumber());
            participant.setStamnumber(Verbond.OTHERS.getStam());
        }
    }
}
