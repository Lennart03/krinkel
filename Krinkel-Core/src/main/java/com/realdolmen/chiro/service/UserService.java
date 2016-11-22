package com.realdolmen.chiro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.vo.SecurityStamNumberVO;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Profile("!test")
public class UserService {

    @Autowired
    private RegistrationParticipantRepository repo;

    @Autowired
    private ChiroColleagueService chiroColleagueService;

    @Autowired
    private ChiroPloegService chiroPloegService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private User currentUser;

    // TODO
    // TODO
    // TODO
    // TODO
    // TODO this is only used in (wrong) tests. It has to go
    public User getUser(String adNumber) {
        return null;
    }

    /**
     * get a participant or volunteer from our DB
     *
     * @param adNumber
     * @return a participant or volunteer from our DB
     */
    public RegistrationParticipant getRegistrationParticipant(String adNumber) {
        RegistrationParticipant participant = repo.findByAdNumber(adNumber);

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
     * get the securityRole with the most privileges
     *
     * @param stamNumbers
     * @return securityRole with the most privileges
     */
//    public SecurityRole getHighestSecurityRole(List<String> stamNumbers) {
//        SecurityRole highestRole = SecurityRole.GROEP;
//        for (String currentStamNumber : stamNumbers) {
//            if (getSecurityRole(currentStamNumber).getValue() > highestRole.getValue()) {
//                highestRole = getSecurityRole(currentStamNumber);
//            }
//        }
//        return highestRole;
//    }


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
        return currentUser;
    }

    /**
     * set the current user. we do this to avoid using the db to much
     *
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
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
     * @return
     * @throws URISyntaxException
     */
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

    public SecurityStamNumberVO getHighestSecurityRoleAndStamNumber(String adNumber, List<String> adminAdNumbers) {
        SecurityStamNumberVO securityStamNumberVO = new SecurityStamNumberVO();
        List<String> stamNumbers = chiroPloegService.getStamNumbers(adNumber);

        SecurityRole highestRole = SecurityRole.GROEP;
        String highestStamNumber = "";
        for (String currentStamNumber : stamNumbers) {
            if (getSecurityRole(currentStamNumber).getValue() >= highestRole.getValue()) {
                highestRole = getSecurityRole(currentStamNumber);
                highestStamNumber = currentStamNumber;
            }
        }

        securityStamNumberVO.setHighestStamNumber(highestStamNumber);
        if (adminAdNumbers.contains(adNumber)) {
             securityStamNumberVO.setHighestRole(SecurityRole.ADMIN);
        } else {
            securityStamNumberVO.setHighestRole(highestRole);
        }
        return securityStamNumberVO;
    }
}
