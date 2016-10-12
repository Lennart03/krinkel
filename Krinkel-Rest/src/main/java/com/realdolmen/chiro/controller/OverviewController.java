package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.service.OverviewService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OverviewController {

//    @Autowired
//    private ChiroUnitService chiroUnitService;

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @Autowired
    private OverviewService overviewService;



    @RequestMapping(value = "/api/participants/{stam}", method = RequestMethod.GET)
    public int findParticipants(@PathVariable("stam") String stamNumber){
        if (!stamNumber.endsWith("00")) {
            return registrationParticipantService.findParticipantsByGroup(stamNumber).size();
        }else if (!stamNumber.endsWith("000")){
            return overviewService.findParticipantsByGewest(stamNumber).size();
        }else{
            return overviewService.findParticipantsByVerbond(stamNumber).size();
        }
    }
}
