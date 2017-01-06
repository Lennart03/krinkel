package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.exception.NoContactFoundException;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by LVDBB73 on 9/12/2016.
 */
public class AdminServiceTest extends SpringIntegrationTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ChiroContactService chiroContactService;

    private RegistrationParticipant registrationParticipant;

    @Test
    public void returnAllAdminTest() {
        List<Admin> admins = adminService.getAdmins();
        Assert.assertEquals(4, admins.size());
    }

    @Test
    public void addNewAdminSucces() throws URISyntaxException, NoContactFoundException, IOException {
        adminService.addNewAdmin(396947);
        List<Admin> admins = adminService.getAdmins();
        Assert.assertEquals(5, admins.size());
        for(Admin admin: admins) {
            if(admin.getAdNumber().equals(396947)){
                Assert.assertEquals(new Integer(396947), admin.getAdNumber());
                Assert.assertEquals("Thomas", admin.getFirstname());
                Assert.assertEquals("thomas.simons@realdolmen.com", admin.getEmail());
                Assert.assertEquals("simons", admin.getLastname());
            }
        }

    }

    @Test
    public void shouldDeleteAnAdmin() {
        adminService.deleteAdmin(1);
        List<Admin> admins = adminService.getAdmins();
        Assert.assertEquals(3, admins.size());
        boolean found = false;
        for(Admin admin : admins) {
            if(admin.getAdNumber().equals(1)){
                found = true;
            }
        }
        Assert.assertFalse(found);

    }

}
