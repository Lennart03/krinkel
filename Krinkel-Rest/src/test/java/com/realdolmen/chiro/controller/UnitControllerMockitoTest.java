package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.ChiroUnitService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import com.realdolmen.chiro.service.UserService;
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
    private UnitController controller;

    @Mock
    private ChiroUnitService unitService;
    
    @Mock
    private RegistrationParticipantService registrationParticipantService;

    @Mock
    private UserService userService;

    public final static String TEST_STAMLETTERS = "LEG ";
    public final static String TEST_STAMCIJFERS = "0000";
    public final static String TEST_STAMNR = "LEG /0000";


    @Test
    public void getUnitUserListReturnsListGivenByUserService() {
        List<User> list = new ArrayList<>();
        Mockito.when(unitService.getUnitUsers(TEST_STAMNR)).thenReturn(list);
        List<User> unitUserList = controller.getUnitUserList(TEST_STAMLETTERS, TEST_STAMCIJFERS);
        Assert.assertSame(list, unitUserList);
    }
    
    @Test
    public void findRegisteredParticipantsByGroupReturnsListOfParticipants(){
    	List<RegistrationParticipant>participants = new ArrayList<>();
    	Mockito.when(registrationParticipantService.findParticipantsByGroup(TEST_STAMNR)).thenReturn(participants);
    	List<RegistrationParticipant>unitParticipants = controller.findRegisteredParticipantsByGroup(TEST_STAMNR);
    	Assert.assertSame(participants, unitParticipants);
    	Assert.assertEquals(participants.size(), unitParticipants.size());
    }
    
    @Test
    public void findRegisteredVolunteersByGroupReturnsListOfVolunteers(){
    	List<RegistrationVolunteer>volunteers = new ArrayList<>();
    	Mockito.when(registrationParticipantService.findVolunteersByGroup(TEST_STAMNR)).thenReturn(volunteers);
    	List<RegistrationVolunteer>unitVolunteers = controller.findRegisteredVolunteersByGroup(TEST_STAMNR);
    	Assert.assertSame(volunteers, unitVolunteers);
    	Assert.assertEquals(volunteers.size(), unitVolunteers.size());
    }
}
