package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
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

import java.util.Date;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationParticipantServiceTest {


    @InjectMocks
    private RegistrationParticipantService registrationParticipantService;

    @Mock
    private RegistrationParticipantRepository repo;

    @Mock
    private MultiSafePayService mspService;

    @Mock
    private ChiroUserAdapter adapter;

    private RegistrationParticipant participant;
    private User user;

    public final static String TEST_AD_NUMBER = "123456";
    public final static String TEST_ORDER_ID = "123456-2134684163";
    public final static String TEST_STAMNR = "LEG/0608";

    @Before
    public void setUp() {
        participant = new RegistrationParticipant();
        participant.setFirstName("NICK");
        participant.setLastName("HANOT");
        participant.setStamnumber("STAMNUMMER");    // don't assign TEST_STAMNR to it
        participant.setAdNumber("ADNUMMER");
        participant.setRole(Role.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());
        participant.setStatus(Status.TO_BE_PAID);

        Address address = new Address();
        address.setCity("REET");
        address.setHouseNumber("69");
        address.setPostalCode(6969);
        address.setStreet("REETSESTEENWEG");
        participant.setAddress(address);

        user = new User();
        user.setAdNumber(TEST_AD_NUMBER);
        user.setStamnummer(TEST_STAMNR);
    }


    @Test
    public void saveShouldReturnParticipantAfter() {
        Mockito.when(repo.save(participant)).thenReturn(participant);
        Mockito.when(adapter.getChiroUser(participant.getAdNumber())).thenReturn(user);
        Assert.assertSame(participant, registrationParticipantService.save(participant));
        Mockito.verify(repo).save(participant);
    }


    @Test
    public void saveShouldReturnNULLWhenExisting() {
        Mockito.when(repo.findByAdNumber("ADNUMMER")).thenReturn(null);
        Mockito.when(adapter.getChiroUser(participant.getAdNumber())).thenReturn(user);

        registrationParticipantService.save(participant);

        Assert.assertSame(null, registrationParticipantService.save(participant));
        Mockito.verify(repo, times(2)).save(participant);
    }

    @Test
    public void saveShouldAskChiroAdapterForStamnrOfParticipantBeingSaved() {
        Mockito.when(adapter.getChiroUser(TEST_AD_NUMBER)).thenReturn(user);
        Mockito.when(repo.findByAdNumber(TEST_AD_NUMBER)).thenReturn(null);

        // make repo save method return its argument
        Mockito.when(repo.save(Mockito.any(RegistrationParticipant.class))).thenAnswer(new Answer<RegistrationParticipant>() {
            @Override
            public RegistrationParticipant answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (RegistrationParticipant) args[0];
            }
        });

        participant.setAdNumber(TEST_AD_NUMBER);
        participant.setStamnumber("aojefaef;hjpaioefj");

        RegistrationParticipant p = registrationParticipantService.save(participant);
        Mockito.verify(adapter, times(1)).getChiroUser(TEST_AD_NUMBER);
        Assert.assertEquals(TEST_STAMNR, p.getStamnumber());
    }


    @Test
    public void updatePaymentStatusCallsMultiSafePayServiceWithCorrectOrderId() {
        Mockito.when(repo.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);
        registrationParticipantService.updatePaymentStatus(TEST_ORDER_ID);

        Mockito.verify(mspService, times(1)).orderIsPaid(TEST_AD_NUMBER);
    }

    @Test
    public void updatePaymentStatusSetsStatusToPaidWhenMultisafepayStatusIsCompleted() {
        Mockito.when(mspService.orderIsPaid(anyString())).thenReturn(true);
        Mockito.when(repo.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);

        registrationParticipantService.updatePaymentStatus(TEST_ORDER_ID);
        Mockito.verify(repo, times(1)).findByAdNumber(TEST_AD_NUMBER);
        Mockito.verify(repo, times(1)).save(participant);
        Assert.assertEquals(Status.PAID, participant.getStatus());
    }
}