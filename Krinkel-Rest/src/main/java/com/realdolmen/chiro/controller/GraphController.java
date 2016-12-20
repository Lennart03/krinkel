package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.GraphLoginCount;
import com.realdolmen.chiro.domain.Verbond;
import com.realdolmen.chiro.domain.units.GraphChiroUnit;
import com.realdolmen.chiro.domain.units.StatusChiroUnit;
import com.realdolmen.chiro.service.GraphChiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


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


    @RequestMapping("/api/graph/uniqueLoginsPerVerbond")
    public SortedMap<Verbond, SortedMap<String, Integer>> getUniqueLoginsPerVerbond(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) throws ParseException {
            System.out.println("startDate = " + startDate.toString());
            System.out.println("endDate = " + endDate.toString());
        Date start;
        Date end;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        start = simpleDateFormat.parse(startDate);
        end = simpleDateFormat.parse(endDate);



        System.out.println("end date " +end.toString());

        return graphChiroService.getLoginData(start,end);
    }
    /*
    @RequestMapping("/api/graph/uniqueLoginsPerVerbondLastTwoWeeks")
    public SortedMap<Verbond, SortedMap<String, Integer>> getUniqueLoginsPerVerbondLastTwoWeeks() {
        return graphChiroService.getLoginDataFromLastTwoWeeks();
    }*/
}
