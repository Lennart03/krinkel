package com.realdolmen.chiro.controller;

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
    private ChiroUnitService service;

    @RequestMapping(method = RequestMethod.GET, params = {"gewest"})
    public List<ChiroUnit> allGewestUnits(){
        return service.findGewestUnits();
    }

    @RequestMapping(value="", method = RequestMethod.GET, params ={"verbond"})
    public List<ChiroUnit> allVerbondUnits(){
        return service.findVerbondUnits();
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<ChiroUnit> units(){
        return service.findAll();
    }

    @RequestMapping(value="/{stam}", method = RequestMethod.GET)
    public ChiroUnit singleUnit(@PathVariable("stam") String stam){
        return service.find(stam);
    }
}
