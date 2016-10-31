package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationParticipantService {
	public final static Integer PRICE_IN_EUROCENTS = 11000;

	private Logger logger = LoggerFactory.getLogger(RegistrationParticipantService.class);

	@Autowired
	private RegistrationParticipantRepository registrationParticipantRepository;

	@Autowired
	private RegistrationCommunicationRepository registrationCommunicationRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private MultiSafePayService mspService;

	public RegistrationParticipant save(RegistrationParticipant participant){
		User chiroUser = userService.getUser(participant.getAdNumber());
		RegistrationParticipant participantFromOurDB = registrationParticipantRepository.findByAdNumber(participant.getAdNumber());

		if(participantFromOurDB == null && chiroUser != null){
			String stamnummer = chiroUser.getStamnummer();
			User currentUser = userService.getCurrentUser();
			participant.setStamnumber(stamnummer);
			participant.setRegisteredBy(currentUser.getAdNumber());
			return registrationParticipantRepository.save(participant);
		} else if (participantFromOurDB != null && participantFromOurDB.getStatus().equals(Status.TO_BE_PAID) && chiroUser!=null){
			participant.setId(participantFromOurDB.getId());
			User currentUser = userService.getCurrentUser();
			participantFromOurDB.setRegisteredBy(currentUser.getAdNumber());
			return registrationParticipantRepository.save(participant);
		} else if (participantFromOurDB != null && (participantFromOurDB.getStatus().equals(Status.PAID)) || participantFromOurDB.getStatus().equals(Status.CONFIRMED)){
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

			if (mspService.orderIsPaid(testOrderId)) {
				participant.setStatus(Status.PAID);
				logger.info("participant " + participant.getAdNumber() + " on status 'PAID'");

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

	public List<RegistrationParticipant> findParticipantsByGroup(String stamNumber) {
		List<RegistrationParticipant> participants = registrationParticipantRepository.findParticipantsByGroupWithStatusConfirmedOrPaid(stamNumber);
//=======
//		List<RegistrationParticipant> participants = registrationParticipantRepository.findParticipantsByGroup(stamNumber);
//>>>>>>> devel
		List<RegistrationParticipant> results = new ArrayList<>();
		for (RegistrationParticipant participant : participants) {
			if (!(participant instanceof RegistrationVolunteer)) {
				results.add(participant);
			}
		}
		return results;
	}

	public List<RegistrationVolunteer> findVolunteersByGroup(String stamNumber) {
		List<RegistrationParticipant> participants = registrationParticipantRepository.findParticipantsByGroupWithStatusConfirmedOrPaid(stamNumber);
//=======
//		List<RegistrationParticipant> participants = registrationParticipantRepository.findParticipantsByGroup(stamNumber);
//>>>>>>> devel
		List<RegistrationVolunteer> results = new ArrayList<>();
		for (RegistrationParticipant participant : participants) {
			if (participant instanceof RegistrationVolunteer) {
				results.add((RegistrationVolunteer) participant);
			}
		}
		return results;
	}
}
