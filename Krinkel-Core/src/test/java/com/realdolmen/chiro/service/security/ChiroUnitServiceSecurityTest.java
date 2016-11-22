package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChiroUnitServiceSecurityTest {
    @InjectMocks
    ChiroUnitServiceSecurity chiroUnitServiceSecurity;

    @Mock
    private UserService userService;

    @Test
    public void hasPermissionToGetUsersReturnsFalseWhenNoLoggedInUser(){
        boolean b = chiroUnitServiceSecurity.hasPermissionToGetColleagues();
        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToGetUsersReturnsTrueWhenLoggedInUser(){
        User user = new User();
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        boolean b = chiroUnitServiceSecurity.hasPermissionToGetColleagues();

        Assert.assertEquals(true, b);
    }

    @Test
    public void hasPermissionToSeeColleaguesReturnsFalseWhenNoLoggedInUser(){
        boolean b = chiroUnitServiceSecurity.hasPermissionToSeeColleagues(null);

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPesmissionToSeeColleaguesReturnsFalseWhenNumbersNotEqual(){
        User currentUser = new User();
        currentUser.setStamnummer("123");
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        User user = new User();
        user.setStamnummer("321");

        boolean b = chiroUnitServiceSecurity.hasPermissionToSeeColleagues(user);
        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPesmissionToSeeColleaguesReturnsTrueWhenNumbersEqual(){
        User currentUser = new User();
        currentUser.setStamnummer("123");
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = chiroUnitServiceSecurity.hasPermissionToSeeColleagues(currentUser);
        Assert.assertEquals(true, b);
    }

//    @Test
//    public void hasPermissionToGetVolunteersReturnsFalseWhenNoLoggedInUser(){
//        boolean b = chiroUnitServiceSecurity.hasPermissionToGetVolunteers();
//
//        Assert.assertEquals(false, b);
//    }
//
//    @Test
//    public void hasPermissionToGetVolunteersReturnsFalseWhenRoleNotAdmin(){
//        User currentUser = new User();
//        currentUser.setRole(SecurityRole.GROEP);
//        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
//
//        boolean b = chiroUnitServiceSecurity.hasPermissionToGetVolunteers();
//        Assert.assertEquals(false, b);
//    }
//
//    @Test
//    public void hasPermissionToGetVolunteersReturnsTrueWhenRoleAdmin(){
//        User currentUser = new User();
//        currentUser.setRole(SecurityRole.ADMIN);
//        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
//
//        boolean b = chiroUnitServiceSecurity.hasPermissionToGetVolunteers();
//        Assert.assertEquals(true, b);
//    }
//
//    @Test
//    public void hasPermissionToGetParticipantsReturnsFalseWhenNoLoggedInUser(){
//        boolean b = chiroUnitServiceSecurity.hasPermissionToGetParticipants();
//
//        Assert.assertEquals(false, b);
//    }
//
//    @Test
//    public void hasPermissionToGetParticipantsReturnsFalseWhenRoleNotAdmin(){
//        User currentUser = new User();
//        currentUser.setRole(SecurityRole.GROEP);
//        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
//
//        boolean b = chiroUnitServiceSecurity.hasPermissionToGetParticipants();
//        Assert.assertEquals(false, b);
//    }
//
//    @Test
//    public void hasPermissionToGetParticipantsReturnsTrueWhenRoleAdmin(){
//        User currentUser = new User();
//        currentUser.setRole(SecurityRole.ADMIN);
//        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
//
//        boolean b = chiroUnitServiceSecurity.hasPermissionToGetParticipants();
//        Assert.assertEquals(true, b);
//    }

    @Test
    public void hasPermissionToFindUnitsReturnsFalseWhenNoLoggedInUser(){
        boolean b = chiroUnitServiceSecurity.hasPermissionToFindUnits();

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToFindUnitsReturnsFalseWhenRoleNotAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.GROEP);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = chiroUnitServiceSecurity.hasPermissionToFindUnits();
        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToFindUnitsReturnsTrueWhenRoleAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.ADMIN);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = chiroUnitServiceSecurity.hasPermissionToFindUnits();
        Assert.assertEquals(true, b);
    }

    @Test
    public void hasPermissionToFindVerbondenReturnsFalseWhenNoLoggedInUser(){
        boolean b = chiroUnitServiceSecurity.hasPermissionToFindVerbonden();

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToFindVerbondenReturnsFalseWhenRoleNotAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.GROEP);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = chiroUnitServiceSecurity.hasPermissionToFindVerbonden();
        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToFindVerbondenReturnsTrueWhenRoleAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.ADMIN);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = chiroUnitServiceSecurity.hasPermissionToFindVerbonden();
        Assert.assertEquals(true, b);
    }
}
