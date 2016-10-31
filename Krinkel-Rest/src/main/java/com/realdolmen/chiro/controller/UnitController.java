package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import com.realdolmen.chiro.service.UserService;
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
 * /units/{stamletters}/{stamnummers}/users = all users with given stamnummer ({stamletters}/{stamnummers} = stamnummer)
 */
@RestController
@RequestMapping(value = "/api/units", produces = "application/json")
public class UnitController {

    @Autowired
    private ChiroUnitService unitService;
    
//    @Autowired
//    private RegistrationParticipantService registrationParticipantsService;

//    @Autowired
//    private UserService userService;

    //TODO CHECK IF WE EVEN NEED THIS
//    @RequestMapping(method = RequestMethod.GET, params = {"gewest"})
//    public List<ChiroUnit> allGewestUnits() {
//        return unitService.findGewestUnits();
//    }

    /**
     * first thing you see in the table (the list of verbonden)
     * @return list of all Verbonden
     */
    @RequestMapping(value = "", method = RequestMethod.GET, params = {"verbond"})
    public List<ChiroUnit> allVerbondUnits() {
        return unitService.findVerbondUnits();
    }

    //TODO CHECK IF WE EVEN NEED THIS
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public List<ChiroUnit> units() {
//        return unitService.findAll();
//    }

    /**
     * method used in the frondend when getting the colleagues. (select person to enroll in krinkel)
     *
     * @param stamletters first part of stamnumber
     * @param stamnummers second part of stamnumber
     * @return users with same stamnumber
     */
    @RequestMapping(value = "{stamletters}/{stamnummers}/users")
    public List<User> getUnitUserList(@PathVariable("stamletters") String stamletters, @PathVariable("stamnummers") String stamnummers) {
        String stamnr = stamletters + "/" + stamnummers;
        return unitService.getUnitUsers(stamnr);
    }

    /**
     * used in the table to get all the gewesten en groepen
     * @param stam
     * @return a list of gewesten or groepen
     */
    @RequestMapping(value = "/{stam}", method = RequestMethod.GET)
    public ChiroUnit singleUnit(@PathVariable("stam") String stam) {
        return unitService.find(stam);
    }

    /**
     * method used to fill the table with the participant data (allergies and stuff)
     *
     * @param stamNumber
     * @return participants within the unit with the specified stamNumber
     */
    @RequestMapping(value = "/{stamNummer}/participants", method = RequestMethod.GET)
    public List<RegistrationParticipant>findRegisteredParticipantsByGroup(@PathVariable("stamNummer") String stamNumber){
    	//return registrationParticipantsService.findParticipantsByGroup(stamNumber);
    	return unitService.findParticipantsByChiroUnit(stamNumber);
    }

    /**
     * method used to fill the table with the volunteer data (allergies and stuff)
     *
     * @param stamNumber
     * @return volunteers within the unit with the specified stamNumber
     */
    @RequestMapping(value = "/{stamNummer}/volunteers", method = RequestMethod.GET)
    public List<RegistrationVolunteer>findRegisteredVolunteersByGroup(@PathVariable("stamNummer") String stamNumber){
    	//return registrationParticipantsService.findVolunteersByGroup(stamNumber);
    	return unitService.findVolunteersByChiroUnit(stamNumber);
    }
}
