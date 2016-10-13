package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationVolunteerServiceTest {
    @InjectMocks
    private RegistrationVolunteerService service;

    @Mock
    private ChiroUserAdapter adapter;

    @Mock
    private RegistrationVolunteerRepository repo;

    public final static String TEST_AD_NUMBER = "123456";
    public final static String TEST_STAMNR = "LEG/0608";

    private RegistrationVolunteer volunteer;
    private User user;


    @Before
    public void init() {
        volunteer = new RegistrationVolunteer();
        volunteer.setStamnumber(TEST_STAMNR);
        volunteer.setAdNumber(TEST_AD_NUMBER);

        user = new User();
        user.setStamnummer(TEST_STAMNR);
        user.setAdNumber(TEST_AD_NUMBER);
    }

    @Test
    public void saveShouldAskChiroAdapterForStamnrOfParticipantBeingSaved() {
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(user);
        Mockito.when(repo.findByAdNumber(TEST_AD_NUMBER)).thenReturn(null);

        // make repo save method return its argument
        Mockito.when(repo.save(Mockito.any(RegistrationVolunteer.class))).thenAnswer(new Answer<RegistrationVolunteer>() {
            @Override
            public RegistrationVolunteer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (RegistrationVolunteer) args[0];
            }
        });

        volunteer.setAdNumber(TEST_AD_NUMBER);
        volunteer.setStamnumber("aojefaef;hjpaioefj"); // make sure it's different from what it should be

        RegistrationParticipant v = service.save(volunteer);
        Mockito.verify(adapter, times(1)).getChiroUser(TEST_AD_NUMBER);
        Assert.assertEquals(TEST_STAMNR, v.getStamnumber());
    }
}
