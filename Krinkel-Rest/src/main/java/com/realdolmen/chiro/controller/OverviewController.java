package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.units.ChiroGroepGewestVerbond;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.service.UserService;
import com.realdolmen.chiro.service.VerbondenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by JCPBB69 on 15/12/2016.
 */
@RestController
@RequestMapping(value = "/api/overview", produces = "application/json")
public class OverviewController {
    @Autowired
    private VerbondenService verbondenService;

    @Autowired
    private UserService userService;


    /**
     *
     * @return list of all Verbonden
     */
    @RequestMapping(value = "", method = RequestMethod.GET, params = {"verbonden"})
    public List<ChiroUnit> getVerbonden() {
        //System.err.println("Verbonden in overview controller: " + verbondenService.getVerbonden());
        return verbondenService.getVerbonden();
    }



    @RequestMapping(value = "/gewesten/{verbondStamNummer}", method = RequestMethod.GET)
    public List<ChiroUnit> getGewesten(@PathVariable("verbondStamNummer") String verbondStamNummer) {
        //System.err.println("Gewesten in overview controller: " + verbondenService.getGewesten(verbondStamNummer));
        return verbondenService.getGewesten(verbondStamNummer);
    }

    @RequestMapping(value = "/groepen/{gewestStamNummer}", method = RequestMethod.GET)
    public List<ChiroUnit> getGroepen(@PathVariable("gewestStamNummer") String gewestStamNummer) {
//        System.err.println("Groepen in overview controller: " + verbondenService.getGroepen(gewestStamNummer));
        return verbondenService.getGroepen(gewestStamNummer);
    }

    @RequestMapping(value = "/groep/{groepStamNummer}", method = RequestMethod.GET)
    public List<RegistrationParticipant> getParticipants(@PathVariable("groepStamNummer") String groepStamNummer) {
//        System.err.println("Participants in overview controller: " + verbondenService.getRegistrationParticipants(groepStamNummer));
        return verbondenService.getRegistrationParticipants(groepStamNummer);
    }

    @RequestMapping(value = "/groep/{groepStamNummer}/vrijwilligers", method = RequestMethod.GET)
    public List<RegistrationVolunteer> getVolunteers(@PathVariable("groepStamNummer") String groepStamNummer) {
        //System.err.println("Participants in overview controller: " + verbondenService.getRegistrationVolunteers(groepStamNummer));
        return verbondenService.getRegistrationVolunteers(groepStamNummer);
    }

    @RequestMapping(value = "/campground/{campground}/vrijwilligers", method = RequestMethod.GET)
    public List<RegistrationVolunteer> getVolunteersByCampground (@PathVariable("campground") String campground){

        return verbondenService.getRegistrationVolunteersByCampground(campground);
    }


//    @RequestMapping(value = "/participants/{status}", method = RequestMethod.GET)
//    public List<RegistrationVolunteer> getParticipantsByStatus (@PathVariable("staus") String status){
//        // TODO status enum ophalen op basis van string en die ophalen
////        return verbondenService.getRegistrationVolunteersByCampground(status);
//        return new ArrayList<RegistrationVolunteer>();
//    }

    @RequestMapping(value = "/groepstamnummer/{groepstamnummer}", method = RequestMethod.GET)
    public ChiroGroepGewestVerbond getRawChiroUnitByGroepStamNummer(@PathVariable("groepstamnummer") String groepstamnummer) {
//        System.err.println("INSIDE getRawChiroUnitByGroepStamNummer in overviewcontroller");
        return userService.getChiroUnitByGroepStamNummer(groepstamnummer);
    }
}
