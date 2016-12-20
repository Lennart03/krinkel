package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
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
public class RegistrationParticipantControllerMockitoTest {
    @InjectMocks
    private RegistrationParticipantController registrationParticipantController;

    @Mock
    private MultiSafePayService multiSafePayService;

    @Mock
    private RegistrationParticipantService registrationParticipantService;

    @Mock
    private UserService userService;

    private static String TEST_URL = "apeiofjoiaejfoioijagrapioejfleiofja";  //gibberish so test won't pass by accident
    private RegistrationParticipant p;
    private User currentUser;

    @Before
    public void setUp() {
        p = new RegistrationParticipant();
        p.setAdNumber("abc123");

        currentUser = new User();
    }

    @After
    public void verifyStrict() {
        Mockito.verifyNoMoreInteractions(multiSafePayService);
        //Mockito.verifyNoMoreInteractions(registrationParticipantService);
    }

    @Test
    public void saveReturnsUrlGivenByMultiSafePayService() throws URISyntaxException, MultiSafePayService.InvalidPaymentOrderIdException {
        Mockito.when(registrationParticipantService.getPRICE_IN_EUROCENTS()).thenReturn(110);
        Integer price = registrationParticipantService.getPRICE_IN_EUROCENTS();
        Mockito.when(multiSafePayService.getParticipantPaymentUri(p, price, currentUser)).thenReturn(TEST_URL);
        Mockito.when(registrationParticipantService.save(p)).thenReturn(p);
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        ResponseEntity<?> response = registrationParticipantController.save(p);
        Assert.assertSame(response.getHeaders().getFirst("Location"), TEST_URL);

        Mockito.verify(multiSafePayService).getParticipantPaymentUri(p, price, currentUser);
        Mockito.verify(registrationParticipantService).save(p);
    }
}
