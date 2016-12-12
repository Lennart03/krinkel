package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.TestConfig;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.exception.NoContactFoundException;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by LVDBB73 on 9/12/2016.
 */
@ContextConfiguration(classes={TestConfig.class})
public class AdminServiceTest extends SpringIntegrationTest {

    @Autowired
    @InjectMocks
    private AdminService adminService;

    @Mock
    private ChiroContactService chiroContactService;

    private RegistrationParticipant registrationParticipant;

    @Test
    public void returnAllAdminTest() {
        List<Admin> admins = adminService.getAdmins();
        Assert.assertEquals(4, admins.size());
    }

    @Test
    public void addNewAdminSucces() throws URISyntaxException, NoContactFoundException {
        registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("5");
        registrationParticipant.setEmail("fifthEmail");
        registrationParticipant.setFirstName("fifth");
        registrationParticipant.setLastName("Admin");
        Mockito.when(chiroContactService.getRegistrationParticipant(5)).thenReturn(registrationParticipant);
        adminService.addNewAdmin(5);
        List<Admin> admins = adminService.getAdmins();
        Assert.assertEquals(5, admins.size());
        for(Admin admin: admins) {
            if(admin.getAdNummer().equals(5)){
                Assert.assertEquals(new Integer(5), admin.getAdNummer());
                Assert.assertEquals("fifth", admin.getFirstname());
                Assert.assertEquals("fifthEmail", admin.getEmail());
                Assert.assertEquals("Admin", admin.getLastname());
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
            if(admin.getAdNummer().equals(1)){
                found = true;
            }
        }
        Assert.assertFalse(found);

    }

}
