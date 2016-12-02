package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationVolunteerServiceTest {
    @InjectMocks
    private RegistrationVolunteerService registrationVolunteerService;

    @Mock
    private RegistrationVolunteerRepository registrationVolunteerRepository;

    @Mock
    private UserService userService;

    public final static String TEST_AD_NUMBER = "123456";
    public final static String TEST_STAMNR = "LEG/0608";

    private RegistrationVolunteer volunteer;
    private User user;
    private RegistrationVolunteer registrationVolunteerFromOurDB;


    @Before
    public void init() {
        volunteer = new RegistrationVolunteer();
        volunteer.setStamnumber(TEST_STAMNR);
        volunteer.setAdNumber(TEST_AD_NUMBER);

        user = new User();
        user.setStamnummer(TEST_STAMNR);
        user.setAdNumber(TEST_AD_NUMBER);
        userService.setCurrentUser(user);

        registrationVolunteerFromOurDB = new RegistrationVolunteer();
    }

    @Test
    public void saveShouldReturnVolunteerAfterWhenNotInOurDB() {
        Mockito.when(registrationVolunteerRepository.save(volunteer)).thenReturn(volunteer);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(volunteer, registrationVolunteerRepository.save(volunteer));

        Mockito.verify(registrationVolunteerRepository).save(volunteer);
    }

    @Test
    public void saveShouldReturnParticipantWhenInOurDBAndNotPaid() {
        Mockito.when(registrationVolunteerRepository.save(volunteer)).thenReturn(volunteer);
        registrationVolunteerFromOurDB.setStatus(Status.TO_BE_PAID);
        Mockito.when(registrationVolunteerRepository.findByAdNumber("123456")).thenReturn(registrationVolunteerFromOurDB);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(volunteer, registrationVolunteerService.save(volunteer));

        Mockito.verify(registrationVolunteerRepository).save(volunteer);
    }

    @Test
    public void saveShouldReturnNullWhenInOurDBAndPaid() {
        Mockito.when(registrationVolunteerRepository.save(volunteer)).thenReturn(volunteer);
        registrationVolunteerFromOurDB.setStatus(Status.PAID);
        Mockito.when(registrationVolunteerRepository.findByAdNumber("123456")).thenReturn(registrationVolunteerFromOurDB);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(null, registrationVolunteerService.save(volunteer));

        Mockito.verify(registrationVolunteerRepository).findByAdNumber("123456");
        Mockito.verify(registrationVolunteerRepository, times(0)).save(volunteer);

    }

    @Test
    public void saveShouldReturnNullWhenInOurDBAndConfirmed() {
        Mockito.when(registrationVolunteerRepository.save(volunteer)).thenReturn(volunteer);
        registrationVolunteerFromOurDB.setStatus(Status.CONFIRMED);
        Mockito.when(registrationVolunteerRepository.findByAdNumber("123456")).thenReturn(registrationVolunteerFromOurDB);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Assert.assertSame(null, registrationVolunteerService.save(volunteer));

        Mockito.verify(registrationVolunteerRepository).findByAdNumber("123456");
        Mockito.verify(registrationVolunteerRepository, times(0)).save(volunteer);
    }
}
