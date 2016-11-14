package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import com.realdolmen.chiro.service.UserService;
import org.junit.After;
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
public class UnitControllerMockitoTest {
    @InjectMocks
    private UnitController unitController;

    @Mock
    private ChiroUnitService unitService;
    
    @Mock
    private RegistrationParticipantService registrationParticipantService;

    @Mock
    private UserService userService;

    private final static String TEST_STAMLETTERS = "LEG ";
    private final static String TEST_STAMCIJFERS = "0000";
    private final static String TEST_STAMNR = "LEG /0000";

    @After
    public void verifyStrict(){
        Mockito.verifyNoMoreInteractions(userService);
        //Mockito.verifyNoMoreInteractions(unitService);
    }

    @Test
    public void getUnitUserListReturnsListGivenByUserService() {
        List<User> list = new ArrayList<>();
        Mockito.when(unitService.getUnitUsers(TEST_STAMNR)).thenReturn(list);
        List<User> unitUserList = unitController.getUnitUserList(TEST_STAMLETTERS, TEST_STAMCIJFERS);
        Assert.assertSame(list, unitUserList);

        Mockito.verify(unitService).getUnitUsers(TEST_STAMNR);
    }
    
    @Test
    public void findRegisteredParticipantsByGroupReturnsListOfParticipants(){
    	List<RegistrationParticipant>participants = new ArrayList<>();
    	Mockito.when(registrationParticipantService.findParticipantsByGroup(TEST_STAMNR)).thenReturn(participants);
    	List<RegistrationParticipant>unitParticipants = unitController.findRegisteredParticipantsByGroup(TEST_STAMNR);
    	//Assert.assertSame(participants, unitParticipants);
    	Assert.assertEquals(participants.size(), unitParticipants.size());
    }
    
    @Test
    public void findRegisteredVolunteersByGroupReturnsListOfVolunteers(){
    	List<RegistrationVolunteer>volunteers = new ArrayList<>();
    	Mockito.when(registrationParticipantService.findVolunteersByGroup(TEST_STAMNR)).thenReturn(volunteers);
    	List<RegistrationVolunteer>unitVolunteers = unitController.findRegisteredVolunteersByGroup(TEST_STAMNR);
    	//Assert.assertSame(volunteers, unitVolunteers);
    	Assert.assertEquals(volunteers.size(), unitVolunteers.size());
    }
}
