package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.service.ChiroUnitService;
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

    /**
     *
     * @return list of all Verbonden
     */
    @RequestMapping(value = "", method = RequestMethod.GET, params = {"verbonden"})
    public List<ChiroUnit> getVerbonden() {
        System.err.println("Verbonden in overview controller: " + verbondenService.getVerbonden());
        return verbondenService.getVerbonden();
    }

    @RequestMapping(value = "/gewesten/{verbondStamNummer}", method = RequestMethod.GET)
    public List<ChiroUnit> getGewesten(@PathVariable("verbondStamNummer") String verbondStamNummer) {
        System.err.println("Gewesten in overview controller: " + verbondenService.getGewesten(verbondStamNummer));
        return verbondenService.getGewesten(verbondStamNummer);
    }

    @RequestMapping(value = "/groepen/{groepStamNummer}", method = RequestMethod.GET)
    public List<ChiroUnit> getGroepen(@PathVariable("groepStamNummer") String groepStamNummer) {
        System.err.println("Groepen in overview controller: " + verbondenService.getGroepen(groepStamNummer));
        return verbondenService.getGroepen(groepStamNummer);
    }
}
