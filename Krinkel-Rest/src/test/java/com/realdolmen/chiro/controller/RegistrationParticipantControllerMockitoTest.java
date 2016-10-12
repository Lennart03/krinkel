package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
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
    private RegistrationParticipantController controller;

    @Mock
    private MultiSafePayService mspService;

    @Mock
    private RegistrationParticipantService rpService;


    public static String TEST_URL = "apeiofjoiaejfoioijagrapioejfleiofja";  //gibberish so test won't pass by accident
    private RegistrationParticipant p;

    @Before
    public void init() {
        p = new RegistrationParticipant();
        p.setAdNumber("abc123");
    }


    @Test
    public void saveReturnsUrlGivenByMultiSafePayService() throws URISyntaxException {
        Mockito.when(mspService.getParticipantPaymentUri(p, 11000)).thenReturn(TEST_URL);
        Mockito.when(rpService.save(p)).thenReturn(p);

        ResponseEntity<?> response = controller.save(p);
        Assert.assertSame(response.getHeaders().getFirst("Location"), TEST_URL);
    }
}
