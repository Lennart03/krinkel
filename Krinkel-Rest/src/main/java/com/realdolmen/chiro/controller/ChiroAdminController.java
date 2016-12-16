package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.exception.NoContactFoundException;
import com.realdolmen.chiro.service.AdminService;
import com.realdolmen.chiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
    public void addNewAdmin(@PathVariable Integer adnummer){
        System.out.println("Retrieved a post request in method addNewAdmin() with parameter: " + adnummer);
        try {
            adminService.addNewAdmin(adnummer);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoContactFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/api/admin")
    public List<Admin> getAdmins() {
        System.out.println("Received get from angular in method getAdmins()");
        System.out.println("Going to return: " + adminService.getAdmins());
        return adminService.getAdmins();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/admin/{adnummer}")
    public String getChiroMember(@PathVariable Integer adnummer) {
        System.out.println("Received get from angular in method getChiroMember()");
        System.out.println("Parameter is : " + adnummer);
        try {
            System.out.println("Retrieve from the adminService: " + adminService.getChiroMember(adnummer));
            return adminService.getChiroMember(adnummer);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/admin/delete/{adnummer}")
    public void deleteAdmin(@PathVariable Integer adnummer) {
        System.out.println("Retrieved a post request in method deleteAdmin() with parameter: " + adnummer);
        adminService.deleteAdmin(adnummer);
    }


}
