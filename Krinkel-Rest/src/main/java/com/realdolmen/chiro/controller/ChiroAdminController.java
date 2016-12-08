package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.service.AdminService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by LVDBB73 on 8/12/2016.
 */
@RestController
public class ChiroAdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.POST, value = "/api/admin/{adnummer}")
    public String addNewAdmin(@PathVariable Integer adnummer){
        try {
            adminService.addNewAdmin(adnummer);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "redirect:/api/admin";
        }
        return "redirect:/api/admin";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/api/admin")
    public List<Admin> getAdmins() {
        return adminService.getAdmins();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/admin/{adnummer}")
    public void getChiroMember(@PathVariable Integer adnummer) {

    }


}
