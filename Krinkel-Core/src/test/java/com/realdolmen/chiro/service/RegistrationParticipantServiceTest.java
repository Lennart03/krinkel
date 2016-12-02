package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.EventRole;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.repository.RegistrationCommunicationRepository;
import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
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
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Mock
    private MultiSafePayService multiSafePayService;

    @Mock
    private RegistrationCommunicationRepository registrationCommunicationRepository;

    @Mock
    private UserService userService;

    private RegistrationParticipant participant;
    private RegistrationParticipant registrationParticipantFromOurDB;
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
        participant.setEventRole(EventRole.MENTOR);
        participant.setGender(Gender.X);
        participant.setBirthdate(new Date());
        participant.setStatus(Status.TO_BE_PAID);

        registrationParticipantFromOurDB = new RegistrationParticipant();

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
    public void saveShouldReturnParticipantAfterWhenNotInOurDB() {
        Mockito.when(registrationParticipantRepository.save(participant)).thenReturn(participant);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(participant, registrationParticipantService.save(participant));

        Mockito.verify(registrationParticipantRepository).save(participant);
    }

    @Test
    public void saveShouldReturnParticipantWhenInOurDBAndNotPaid(){
        Mockito.when(registrationParticipantRepository.save(participant)).thenReturn(participant);
        registrationParticipantFromOurDB.setStatus(Status.TO_BE_PAID);
        Mockito.when(registrationParticipantRepository.findByAdNumber("ADNUMMER")).thenReturn(registrationParticipantFromOurDB);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(participant, registrationParticipantService.save(participant));

        Mockito.verify(registrationParticipantRepository).save(participant);
    }

    @Test
    public void saveShouldReturnNullWhenInOurDBAndPaid(){
        Mockito.when(registrationParticipantRepository.save(participant)).thenReturn(participant);
        registrationParticipantFromOurDB.setStatus(Status.PAID);
        Mockito.when(registrationParticipantRepository.findByAdNumber("ADNUMMER")).thenReturn(registrationParticipantFromOurDB);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(null, registrationParticipantService.save(participant));

        Mockito.verify(registrationParticipantRepository).findByAdNumber("ADNUMMER");
        Mockito.verify(registrationParticipantRepository, times(0)).save(participant);

    }

    @Test
    public void saveShouldReturnNullWhenInOurDBAndConfirmed(){
        Mockito.when(registrationParticipantRepository.save(participant)).thenReturn(participant);
        registrationParticipantFromOurDB.setStatus(Status.CONFIRMED);
        Mockito.when(registrationParticipantRepository.findByAdNumber("ADNUMMER")).thenReturn(registrationParticipantFromOurDB);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(null, registrationParticipantService.save(participant));

        Mockito.verify(registrationParticipantRepository).findByAdNumber("ADNUMMER");
        Mockito.verify(registrationParticipantRepository, times(0)).save(participant);
    }

    @Test
    public void saveSetsRegisteredStatusWhenSavingOwn(){
        user.setAdNumber("ADNUMMER");
        Mockito.when(registrationParticipantRepository.save(participant)).thenReturn(participant);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(participant, registrationParticipantService.save(participant));

        Mockito.verify(registrationParticipantRepository).save(participant);
        Mockito.verify(userService).getCurrentUser();
    }

    @Test
    public void updatePaymentStatusCallsMultiSafePayServiceWithCorrectOrderId() {
        Mockito.when(registrationParticipantRepository.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);
        registrationParticipantService.updatePaymentStatus(TEST_ORDER_ID);

        Mockito.verify(multiSafePayService, times(1)).orderIsPaid(TEST_ORDER_ID);
    }

    @Test
    public void updatePaymentStatusSetsStatusToPaidWhenMultisafepayStatusIsCompleted() {
        Mockito.when(multiSafePayService.orderIsPaid(anyString())).thenReturn(true);
        Mockito.when(registrationParticipantRepository.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);
        Mockito.when(registrationCommunicationRepository.findByAdNumber("ADNUMMER")).thenReturn(null);

        registrationParticipantService.updatePaymentStatus(TEST_ORDER_ID);
        Mockito.verify(registrationParticipantRepository, times(1)).findByAdNumber(TEST_AD_NUMBER);
        Mockito.verify(registrationParticipantRepository, times(1)).save(participant);
        Assert.assertEquals(Status.PAID, participant.getStatus());
    }

    @Test
    public void updatePaymentStatusCallsRepositoryWithAdNumber() {
        Mockito.when(registrationParticipantRepository.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);
        registrationParticipantService.updatePaymentStatus(TEST_ORDER_ID);
        Mockito.verify(registrationParticipantRepository, times(1)).findByAdNumber(TEST_AD_NUMBER);
        Mockito.verify(multiSafePayService, times(1)).orderIsPaid(TEST_ORDER_ID);
    }

    @Test
    public void updatePaymentStatusSetStatusToConfirmedWhenOwn(){
        participant.setRegisteredBy("ADNUMMER");
        Mockito.when(multiSafePayService.orderIsPaid(anyString())).thenReturn(true);
        Mockito.when(registrationParticipantRepository.findByAdNumber(TEST_AD_NUMBER)).thenReturn(participant);
        Mockito.when(registrationCommunicationRepository.findByAdNumber("ADNUMMER")).thenReturn(null);

        registrationParticipantService.updatePaymentStatus(TEST_ORDER_ID);
        Mockito.verify(registrationParticipantRepository, times(1)).findByAdNumber(TEST_AD_NUMBER);
        Mockito.verify(registrationParticipantRepository, times(1)).save(participant);
        Assert.assertEquals(Status.CONFIRMED, participant.getStatus());
    }
}