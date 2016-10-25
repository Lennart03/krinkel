package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
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
 * /units/{stamletters}/{stamnummers}/users = all users with given stamnummer ({stamletters}/{stamnummers} = stamnummer)
 */
@RestController
@RequestMapping(value = "/api/units", produces = "application/json")
public class UnitController {

    @Autowired
    private ChiroUnitService unitService;

    @Autowired
    private UserService userService;


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
    //@PreAuthorize("#stam == 'LEG0000'")
    @PreAuthorize("#stam eq @userService.currentUser.normalizedStamNumber")
    public ChiroUnit singleUnit(@PathVariable("stam") String stam) {
        System.out.println("***********" + userService.getCurrentUser().getStamnummer());
        return unitService.find(stam);
    }
}
