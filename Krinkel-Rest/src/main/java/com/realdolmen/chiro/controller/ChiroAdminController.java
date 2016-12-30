package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.domain.units.SuperAdmin;
import com.realdolmen.chiro.exception.NoContactFoundException;
import com.realdolmen.chiro.service.AdminService;
import com.sun.org.apache.xpath.internal.operations.Bool;
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

    @RequestMapping(method = RequestMethod.POST, value = "/api/admins/{adNumber}")
    public void addNewAdmin(@PathVariable Integer adNumber){
        try {
            adminService.addNewAdmin(adNumber);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoContactFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/api/admins")
    public List<Admin> getAdmins() {
        return adminService.getAdmins();
    }

    @RequestMapping(method = RequestMethod.GET, value = "api/superadmins")
    public List<Integer> getSuperAdmins() {return adminService.getSuperAdmins(); }

    @RequestMapping(method = RequestMethod.GET, value = "api/superadmins/{adNumber}")
    public Boolean isSuperAdmin(@PathVariable Integer adNumber) {
        System.out.println("Request is super admin: " + adNumber);
        return adminService.isSuperAdmin(adNumber);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/admins/{adNumber}")
    public String getChiroMember(@PathVariable Integer adNumber) {
        try {
            return adminService.getChiroMember(adNumber);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "api/admins/{adNumber}")
    public void deleteAdmin(@PathVariable Integer adNumber) {
        adminService.deleteAdmin(adNumber);
    }


}
