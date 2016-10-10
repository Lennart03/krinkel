package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.ChiroGroup;
import com.realdolmen.chiro.repository.ChiroGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OverviewController {

    @Autowired
    private ChiroGroupRepository repository;

    @RequestMapping(value = "/api/groups/all", produces = "application/json")
    public List<ChiroGroup> allGroups(){
        return repository.findAll();
    }
}
