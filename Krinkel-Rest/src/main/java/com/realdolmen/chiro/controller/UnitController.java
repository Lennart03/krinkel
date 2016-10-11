package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.ChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/units", produces = "application/json")
public class UnitController {

    @Autowired
    private ChiroUnitRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<ChiroUnit> all(){
        return repository.findAll();
    }

    @RequestMapping(value = "{stam}", method = RequestMethod.GET)
    public ChiroUnit findUnit(@PathVariable("stam")  String stamNumber){
        // TODO: Different fetch strategy:  "AG /0103" versus "AG0103". :s
        return repository.findOne(stamNumber);
    }

    @RequestMapping(value = "participants/{stam}", method = RequestMethod.GET)
    public int findParticipants(@PathVariable("stam") String stamNumber){
        return repository.findParticipantsByUnit(stamNumber).size();
    }
}
