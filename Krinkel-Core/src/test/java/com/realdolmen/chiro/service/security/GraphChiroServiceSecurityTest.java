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
public class GraphChiroServiceSecurityTest {
    @InjectMocks
    GraphChiroServiceSecurity graphChiroServiceSecurity;

    @Mock
    UserService userService;

    @Test
    public void hasPermissionToMakeSunGraphReturnsFalseWhenNoLoggedInUser(){
        boolean b = graphChiroServiceSecurity.hasPermissionToMakeSunGraph();

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToMakeSunGraphReturnsFalseWhenRoleNotAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.GROEP);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = graphChiroServiceSecurity.hasPermissionToMakeSunGraph();
        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToMakeSunGraphReturnsTrueWhenRoleAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.ADMIN);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = graphChiroServiceSecurity.hasPermissionToMakeSunGraph();
        Assert.assertEquals(true, b);
    }

    @Test
    public void hasPermissionToMakeStatusGraphReturnsFalseWhenNoLoggedInUser(){
        boolean b = graphChiroServiceSecurity.hasPermissionToMakeStatusGraph();

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToMakeStatusGraphReturnsFalseWhenRoleNotAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.GROEP);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = graphChiroServiceSecurity.hasPermissionToMakeStatusGraph();
        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToMakeStatusGraphReturnsTrueWhenRoleAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.ADMIN);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = graphChiroServiceSecurity.hasPermissionToMakeStatusGraph();
        Assert.assertEquals(true, b);
    }

    @Test
    public void hasPermissionToGetLoginDataReturnsFalseWhenNoLoggedInUser(){
        boolean b = graphChiroServiceSecurity.hasPermissionToGetLoginData();

        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToGetLoginDataReturnsFalseWhenRoleNotAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.GROEP);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = graphChiroServiceSecurity.hasPermissionToGetLoginData();
        Assert.assertEquals(false, b);
    }

    @Test
    public void hasPermissionToGetLoginDataReturnsTrueWhenRoleAdmin(){
        User currentUser = new User();
        currentUser.setRole(SecurityRole.ADMIN);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        boolean b = graphChiroServiceSecurity.hasPermissionToGetLoginData();
        Assert.assertEquals(true, b);
    }

}
