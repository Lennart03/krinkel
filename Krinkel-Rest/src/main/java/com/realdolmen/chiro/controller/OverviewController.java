package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OverviewController {

    @Autowired
    private ChiroUnitRepository repository;

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @RequestMapping(value = "/api/groups/all", produces = "application/json")
    public List<RawChiroUnit> allGroups(){
        return repository.findAll();
    }

    @RequestMapping(value = "participants/{stam}", method = RequestMethod.GET)
    public int findParticipants(@PathVariable("stam") String stamNumber){
        return registrationParticipantService.findParticipantsByUnit(stamNumber).size();
    }
}
