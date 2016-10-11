package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

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

    private RegistrationParticipant participant;
    public final static String TEST_ORDER_ID = "2134684163";

    @Before
    public void setUp() {
        participant = new RegistrationParticipant();
        participant.setFirstName("NICK");
        participant.setLastName("HANOT");
        participant.setStamnumber("STAMNUMMER");
        participant.setAdNumber("ADNUMMER");
        participant.setRole(Role.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());

        Address address = new Address();
        address.setCity("REET");
        address.setHouseNumber("69");
        address.setPostalCode(6969);
        address.setStreet("REETSESTEENWEG");
        participant.setAddress(address);
    }


    @Test
    public void saveShouldReturnParticipantAfter() {
        Mockito.when(repo.save(participant)).thenReturn(participant);
        Assert.assertSame(participant, registrationParticipantService.save(participant));
        Mockito.verify(repo).save(participant);
    }



    @Test
    public void saveShouldReturnNULLWhenExisting() {
        Mockito.when(repo.findByAdNumber("ADNUMMER")).thenReturn(null);
        registrationParticipantService.save(participant);
        Assert.assertSame(null, registrationParticipantService.save(participant));
        Mockito.verify(repo, times(2)).save(participant);
    }


    @Test
    public void updatePaymentStatusCallsMultiSafePayServiceWithCorrectOrderId() {
        registrationParticipantService.updatePaymentStatus(TEST_ORDER_ID);
        Mockito.verify(mspService, times(1)).orderIsPaid(TEST_ORDER_ID);
    }

    @Test
    public void updatePaymentStatusSetsStatusToPaidWhenMultisafepayStatusIsCompleted() {
        Mockito.when(mspService.orderIsPaid(anyString())).thenReturn(true);
        Mockito.when(repo.findByAdNumber(TEST_ORDER_ID)).thenReturn(participant);

        registrationParticipantService.updatePaymentStatus(TEST_ORDER_ID);
        Mockito.verify(repo, times(1)).findByAdNumber(TEST_ORDER_ID);
        Mockito.verify(repo, times(1)).save(participant);
        //TODO check if status is updated correctly

        Mockito.verifyNoMoreInteractions();
    }
}