package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ChiroUnitServiceMockTest {

    @InjectMocks
    private ChiroUnitService unitService;

    @Mock
    private ChiroUserAdapter userAdapter;

    public final static String TEST_STAMNUMMER = "LEG /0000";

    @Test
    public void getUnitUserReturnsListGivenByAdapter() {
        List<User> userList = new ArrayList<>();
        Mockito.when(userAdapter.getColleagues(TEST_STAMNUMMER)).thenReturn(userList);
        List<User> unitUsers = unitService.getUnitUsers(TEST_STAMNUMMER);
        Assert.assertSame(userList, unitUsers);
    }

}
