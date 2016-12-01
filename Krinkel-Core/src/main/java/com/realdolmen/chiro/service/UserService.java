package com.realdolmen.chiro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realdolmen.chiro.component.CASCurrentlyLoggedInUserComponent;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.vo.RolesAndUpperClasses;
import com.realdolmen.chiro.domain.vo.StamNumbersRolesVO;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.util.StamNumberTrimmer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("userService")
@Profile("!test")
public class UserService {

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    private ChiroColleagueService chiroColleagueService;

    @Autowired
    private ChiroPloegService chiroPloegService;

    @Autowired
    private CASCurrentlyLoggedInUserComponent CASCurrentlyLoggedInUserComponent;

    @Autowired
    private StamNumberTrimmer stamNumberTrimmer;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * get a participant or volunteer from our DB
     *
     * @param adNumber
     * @return a participant or volunteer from our DB
     */
    public RegistrationParticipant getRegistrationParticipant(String adNumber) {
        RegistrationParticipant participant = registrationParticipantRepository.findByAdNumber(adNumber);

        return participant;
    }

    public SecurityRole getSecurityRole(String stamNumber) {
        if (stamNumber.matches("NAT\\/0000")) {
            return SecurityRole.NATIONAAL;
        } else if (stamNumber.matches("[A-Z]+ /0000")) {
            return SecurityRole.VERBOND;
        } else if (stamNumber.matches("[A-Z]{3}/[0-9]{2}00") || stamNumber.matches("[A-Z]{2} /[0-9]{2}00")) {
            return SecurityRole.GEWEST;
        } else {
            return SecurityRole.GROEP;
        }
    }

    /**
     * This is legacy code, just let it be
     *
     * @param context
     * @return current user
     */
    public User getCurrentUser(HttpServletRequest context) {
        return getCurrentUser();
    }

    /**
     * @return current user
     */
    public User getCurrentUser() {
        return CASCurrentlyLoggedInUserComponent.getCurrentUser();
    }

    /**
     * set the current user. we do this to avoid using the db too much
     *
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        CASCurrentlyLoggedInUserComponent.setCurrentUser(currentUser);
    }


    /**
     * update the payment status of the current user after he pays
     */
    public void updateCurrentUserPayStatus() {
        User user = getCurrentUser();
        user.setHasPaid(true);
        setCurrentUser(user);
    }

    /**
     * update the registered status of the current user when he gets saved to our DB
     */
    public void updateCurrentUserRegisteredStatus() {
        User user = getCurrentUser();
        user.setRegistered(true);
        setCurrentUser(user);
    }

    /**
     * Get colleagues from ChiroColleagueService and remove the colleagues who are already subscribed.
     *
     * @param adNumber
     * @return returns JSON, we don't need to use these in the backend so we can filter the subscribed users and return the leftovers as JSON.
     * @throws URISyntaxException
     */
    @PreAuthorize("@UserServiceSecurity.hasPermissionToGetColleagues()")
    public List<String> getColleagues(int adNumber) throws URISyntaxException {
        List<String> listOfAvailableColleagues = new ArrayList<>();

        String body = chiroColleagueService.getColleagues(adNumber);

        ObjectMapper mapper = new ObjectMapper();


        try {
            JsonNode jsonNode = mapper.readTree(body);
            JsonNode values = jsonNode.get("values");


            values.forEach(v -> {
                RegistrationParticipant participant = getRegistrationParticipant(v.get("adnr").asText());

                if (participant != null) {
                    if (participant.getStatus() == Status.PAID || participant.getStatus() == Status.CONFIRMED) {
                        return;
                    }
                }

                listOfAvailableColleagues.add(v.toString());
            });

        } catch (IOException e) {
            // Chiro API borked..
            logger.error("Chiro API probably broken again.");
        }


        return listOfAvailableColleagues;
    }

    public StamNumbersRolesVO getAllStamnumbersRolesAndUpperUnits(String adNumber) {
        StamNumbersRolesVO securityStamNumberVO = new StamNumbersRolesVO();

        Map<String, String> stamNumberUpperStamNuber = chiroPloegService.getStamNumbers(adNumber);

        for (Map.Entry<String, String> stamNumbers : stamNumberUpperStamNuber.entrySet()) {
            SecurityRole securityRole = getSecurityRole(stamNumbers.getKey());
            String stamNumberNormalized = stamNumberTrimmer.trim(stamNumbers.getKey());
            String uperStamNumberNormalized = stamNumberTrimmer.trim(stamNumbers.getValue());

            RolesAndUpperClasses rolesUpperClasses = new RolesAndUpperClasses(securityRole, uperStamNumberNormalized);

            securityStamNumberVO.getRolesAndUpperClassesByStam().put(stamNumberNormalized, rolesUpperClasses);
            securityStamNumberVO.setStamNumber(stamNumberNormalized);
        }

        return securityStamNumberVO;
    }
}
