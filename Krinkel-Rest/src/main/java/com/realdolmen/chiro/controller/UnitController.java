package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.service.ChiroUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/units", produces = "application/json")
public class UnitController {

    @Autowired
    private ChiroUnitService chiroUnitService;

    /**
     * first thing you see in the table (the list of verbonden)
     *
     * @return list of all Verbonden
     */
    @RequestMapping(value = "", method = RequestMethod.GET, params = {"verbond"})
    public List<ChiroUnit> allVerbondUnits() {
        return chiroUnitService.findVerbondUnits();
    }

    /**
     * used in the table to get all the gewesten en groepen
     *
     * @param stam
     * @return a list of gewesten or groepen
     */
    @RequestMapping(value = "/{stam}", method = RequestMethod.GET)
    public List<ChiroUnit> subUnits(@PathVariable("stam") String stam) {
        return chiroUnitService.findSubUnits(stam);
    }

    /**
     * method used to fill the table with the participant data (allergies and stuff)
     *
     * @param stamNumber
     * @return participants within the unit with the specified stamNumber
     */
    @RequestMapping(value = "/{stamNummer}/participants", method = RequestMethod.GET)
    public List<RegistrationParticipant> findRegisteredParticipantsByGroup(@PathVariable("stamNummer") String stamNumber) {
        return chiroUnitService.findParticipantsByChiroUnit(stamNumber);
    }

    /**
     * method used to fill the table with the volunteer data (allergies and stuff)
     *
     * @param stamNumber
     * @return volunteers within the unit with the specified stamNumber
     */
    @RequestMapping(value = "/{stamNummer}/volunteers", method = RequestMethod.GET)
    public List<RegistrationVolunteer> findRegisteredVolunteersByGroup(@PathVariable("stamNummer") String stamNumber) {
        return chiroUnitService.findVolunteersByChiroUnit(stamNumber);
    }
}
