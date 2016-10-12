package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.controller.validation.EnableRestErrorHandling;
import com.realdolmen.chiro.domain.Colleague;
import com.realdolmen.chiro.service.ColleagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ColleagueController {

    @Autowired
    private ColleagueService colleagueService;

    @RequestMapping(method = RequestMethod.GET, value="/api/colleagues", produces = "application/json")
    public List<Colleague> getColleagues(@RequestParam("ad") String adNumber){
        return colleagueService.getColleagues(adNumber);
    }
}
