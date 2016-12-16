package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.service.GenerateGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by MHSBB71 on 15/12/2016.
 */
public class GenerateGroupsController {
    @Autowired
    GenerateGroupsService generateGroupsService;

    @RequestMapping(value = "/tools/generate-groups", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<List<RegistrationParticipant>> generateGroups() {
        System.out.println("generateGroups");
        return generateGroupsService.generateRandomGroups();
    }
}
