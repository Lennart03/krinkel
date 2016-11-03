package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.GraphLoginCount;
import com.realdolmen.chiro.domain.units.GraphChiroUnit;
import com.realdolmen.chiro.domain.units.StatusChiroUnit;
import com.realdolmen.chiro.service.GraphChiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class GraphController {
    @Autowired
    private GraphChiroService graphChiroService;

    @RequestMapping("/api/graph/sun")
    public GraphChiroUnit getGraphSun() {
        return graphChiroService.summary();
    }

    @RequestMapping("/api/graph/status")
    public StatusChiroUnit getStatus() {
        return graphChiroService.getStatusChiro();
    }

    @RequestMapping("/api/graph/login")
    public List<GraphLoginCount> getLoginData() {
        return graphChiroService.getLoginData();
    }
}
