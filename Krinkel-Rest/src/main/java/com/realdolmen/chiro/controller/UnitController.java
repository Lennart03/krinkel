package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.service.ChiroUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * /units  = Everything and alles
 * /units?verbond = Alleen verbonden
 * /units?gewest = Alleen gewesten.
 * /units/{stam} = Eén groep.
 */
@RestController
@RequestMapping(value = "/api/units", produces = "application/json")
public class UnitController {

    @Autowired
    private ChiroUnitService unitService;


    @RequestMapping(method = RequestMethod.GET, params = {"gewest"})
    public List<ChiroUnit> allGewestUnits() {
        return unitService.findGewestUnits();
    }

    @RequestMapping(value = "", method = RequestMethod.GET, params = {"verbond"})
    public List<ChiroUnit> allVerbondUnits() {
        return unitService.findVerbondUnits();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ChiroUnit> units() {
        return unitService.findAll();
    }

    @RequestMapping(value = "{stamletters}/{stamnummers}/users")
    public List<User> getUnitUserList(@PathVariable("stamletters") String stamletters, @PathVariable("stamnummers") String stamnummers) {
        String stamnr = stamletters + "/" + stamnummers;
        return unitService.getUnitUsers(stamnr);
    }

    @RequestMapping(value = "/{stam}", method = RequestMethod.GET)
    public ChiroUnit singleUnit(@PathVariable("stam") String stam) {
        return unitService.find(stam);
    }
}