package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.ChiroColleagueService;
import com.realdolmen.chiro.service.UserService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationParticipantServiceSecurityTest {
    @InjectMocks
    RegistrationParticipantServiceSecurity registrationParticipantServiceSecurity;

    @Mock
    UserService userService;

    @Mock
    ChiroColleagueService chiroColleagueService;

    @Test
    public void hasPermissionToSaveParticipantReturnsFalseWhenNoLoggedInUser(){
        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("123");

        Mockito.when(userService.getCurrentUser()).thenReturn(null);

        boolean b = registrationParticipantServiceSecurity.hasPermissionToSaveParticipant(registrationParticipant);

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToSaveParticipantReturnsFalseWhenNotColleagues(){
        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("123");

        User currentUser = new User();
        currentUser.setAdNumber("456");
        currentUser.setRole(SecurityRole.GROEP);

        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        Mockito.when(chiroColleagueService.isColleague(Integer.parseInt(currentUser.getAdNumber()), Integer.parseInt(registrationParticipant.getAdNumber()))).thenReturn(false);

        boolean b = registrationParticipantServiceSecurity.hasPermissionToSaveParticipant(registrationParticipant);

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToSaveParticipantReturnsTrueWhenColleagues(){
        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("123");

        User currentUser = new User();
        currentUser.setAdNumber("456");
        currentUser.setRole(SecurityRole.GROEP);

        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        Mockito.when(chiroColleagueService.isColleague(456, Integer.parseInt(registrationParticipant.getAdNumber()))).thenReturn(true);

        boolean b = registrationParticipantServiceSecurity.hasPermissionToSaveParticipant(registrationParticipant);

        Assert.assertEquals(true, b);
    }

    @Test
    public void hasPermissionToSaveParticipantReturnsTrueWhenAdminAndNoColleague(){
        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("123");

        User currentUser = new User();
        currentUser.setAdNumber("456");
        currentUser.setRole(SecurityRole.ADMIN);

        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        Mockito.when(chiroColleagueService.isColleague(Integer.parseInt(currentUser.getAdNumber()), Integer.parseInt(registrationParticipant.getAdNumber()))).thenReturn(false);

        boolean b = registrationParticipantServiceSecurity.hasPermissionToSaveParticipant(registrationParticipant);

        Assert.assertEquals(true, b);
    }

    @Test
    public void hasPermissionToSaveParticipantReturnsTrueWhenAdminAndColleague(){
        RegistrationParticipant registrationParticipant = new RegistrationParticipant();
        registrationParticipant.setAdNumber("123");

        User currentUser = new User();
        currentUser.setAdNumber("456");
        currentUser.setRole(SecurityRole.ADMIN);

        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        Mockito.when(chiroColleagueService.isColleague(Integer.parseInt(currentUser.getAdNumber()), Integer.parseInt(registrationParticipant.getAdNumber()))).thenReturn(true);

        boolean b = registrationParticipantServiceSecurity.hasPermissionToSaveParticipant(registrationParticipant);

        Assert.assertEquals(true, b);
    }








}
