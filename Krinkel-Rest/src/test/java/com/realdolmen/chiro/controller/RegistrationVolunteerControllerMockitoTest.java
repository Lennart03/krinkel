package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.service.RegistrationVolunteerService;
import com.realdolmen.chiro.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationVolunteerControllerMockitoTest {
    @InjectMocks
    private RegistrationVolunteerController registrationVolunteerController;

    @Mock
    private MultiSafePayService multiSafePayService;

    @Mock
    private RegistrationVolunteerService registrationVolunteerService;

    @Mock
    private UserService userService;

    private String TEST_URL = "apeiofjoiaejfoioijagrapioejfleiofja";  //gibberish so test won't pass by accident
    private RegistrationVolunteer v;
    private User currentUser;

    @Before
    public void setUp() {
        v = new RegistrationVolunteer();
        v.setAdNumber("abc123");

        currentUser = new User();
    }

    @After
    public void verifyStrict() {
        Mockito.verifyNoMoreInteractions(multiSafePayService);
    }

    @Test
    public void saveReturnsUrlGivenByMultiSafePayService() throws URISyntaxException, MultiSafePayService.InvalidPaymentOrderIdException {
        Mockito.when(registrationVolunteerService.save(v)).thenReturn(v);
        Mockito.when(registrationVolunteerService.getPRICE_IN_EUROCENTS()).thenReturn(60);
        Integer price = registrationVolunteerService.getPRICE_IN_EUROCENTS();
        Mockito.when(multiSafePayService.getVolunteerPaymentUri(v, price, currentUser)).thenReturn(TEST_URL);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);
        ResponseEntity<?> response = registrationVolunteerController.save(v);
        Assert.assertSame(response.getHeaders().getFirst("Location"), TEST_URL);

        Mockito.verify(multiSafePayService).getVolunteerPaymentUri(v, price, currentUser);
        Mockito.verify(registrationVolunteerService).save(v);
    }
}
