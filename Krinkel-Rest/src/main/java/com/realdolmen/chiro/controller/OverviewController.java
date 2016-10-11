package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OverviewController {

    @Autowired
    private ChiroUnitRepository repository;

    @RequestMapping(value = "/api/groups/all", produces = "application/json")
    public List<RawChiroUnit> allGroups(){
        return repository.findAll();
    }
}
