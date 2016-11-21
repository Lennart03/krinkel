package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
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
    private RegistrationParticipantController controller;

    @Mock
    private MultiSafePayService mspService;

    @Mock
    private RegistrationParticipantService rpService;

    private static String TEST_URL = "apeiofjoiaejfoioijagrapioejfleiofja";  //gibberish so test won't pass by accident
    private RegistrationParticipant p;

    @Before
    public void setUp() {
        p = new RegistrationParticipant();
        p.setAdNumber("abc123");
    }

    @After
    public void verifyStrict(){
        Mockito.verifyNoMoreInteractions(mspService);
        //Mockito.verifyNoMoreInteractions(rpService);
    }

    @Test
    public void saveReturnsUrlGivenByMultiSafePayService() throws URISyntaxException, MultiSafePayService.InvalidPaymentOrderIdException {
        Mockito.when(rpService.getPRICE_IN_EUROCENTS()).thenReturn(110);
        Integer price = rpService.getPRICE_IN_EUROCENTS();
    	Mockito.when(mspService.getParticipantPaymentUri(p, price)).thenReturn(TEST_URL);
        Mockito.when(rpService.save(p)).thenReturn(p);

        ResponseEntity<?> response = controller.save(p);
        Assert.assertSame(response.getHeaders().getFirst("Location"), TEST_URL);

        Mockito.verify(mspService).getParticipantPaymentUri(p, price);
        Mockito.verify(rpService).save(p);
    }
}
