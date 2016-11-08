package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.ChiroColleagueService;
import com.realdolmen.chiro.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationVolunteerServiceSecurityTest {
    @InjectMocks
    RegistrationVolunteerServiceSecurity registrationVolunteerServiceSecurity;

    @Mock
    UserService userService;

    @Test
    public void hasPermissionToSaveVolunteerReturnsFalseWhenNoUserLogedIn(){
        RegistrationVolunteer registrationVolunteer = new RegistrationVolunteer();

        Mockito.when(userService.getCurrentUser()).thenReturn(null);

        boolean b = registrationVolunteerServiceSecurity.hasPermissionToSaveVolunteer(registrationVolunteer);

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToSaveVolunteersReturnsFalseWhenAdNumbersNotEqual(){
        RegistrationVolunteer registrationVolunteer = new RegistrationVolunteer();
        registrationVolunteer.setAdNumber("123");

        User currentUser = new User();
        currentUser.setAdNumber("321");

        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        boolean b = registrationVolunteerServiceSecurity.hasPermissionToSaveVolunteer(registrationVolunteer);

        Assert.assertEquals(false, b);

    }

    @Test
    public void hasPermissionToSaveVolunteerReturnsTrueWhenLoggedInAndEquals(){
        RegistrationVolunteer registrationVolunteer = new RegistrationVolunteer();
        registrationVolunteer.setAdNumber("123");

        User currentUser = new User();
        currentUser.setAdNumber("123");

        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        boolean b = registrationVolunteerServiceSecurity.hasPermissionToSaveVolunteer(registrationVolunteer);

        Assert.assertEquals(true, b);
    }

}
