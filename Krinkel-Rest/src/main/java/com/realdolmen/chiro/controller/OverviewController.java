package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.service.OverviewService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/participants")
public class OverviewController {

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @Autowired
    private OverviewService overviewService;



    @RequestMapping(value = "/{stam}", method = RequestMethod.GET)
    public int[] findParticipants(@PathVariable("stam") String stamNumber){
        if (!stamNumber.endsWith("00")) {
            int[] participantsAndVolunteers = new int[2];
            int participants = registrationParticipantService.findParticipantsByGroup(stamNumber).size();
            int volunteers = registrationParticipantService.findVolunteersByGroup(stamNumber).size();
            participantsAndVolunteers[0] = participants;
            participantsAndVolunteers[1] = volunteers;
            return participantsAndVolunteers;
        }else if (!stamNumber.endsWith("000")){
            return overviewService.findParticipantsByGewest(stamNumber);
        }else{
            return overviewService.findParticipantsByVerbond(stamNumber);
        }
    }

    @RequestMapping(value = "/info/{stamNummer}", method = RequestMethod.GET)
    public List[] participantsInfo(@PathVariable("stamNummer") String stamNumber){
        List<RegistrationParticipant> participants = registrationParticipantService.findParticipantsByGroup(stamNumber);
        List<RegistrationVolunteer> volunteers = registrationParticipantService.findVolunteersByGroup(stamNumber);
        List[] userInfo = new List[2];
        userInfo[0] = participants;
        userInfo[1] = volunteers;
        return userInfo;
    }
}
