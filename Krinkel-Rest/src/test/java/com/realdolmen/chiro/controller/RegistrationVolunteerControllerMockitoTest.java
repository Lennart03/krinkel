package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import com.realdolmen.chiro.service.RegistrationVolunteerService;
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
    private RegistrationVolunteerController controller;

    @Mock
    private MultiSafePayService mspService;

    @Mock
    private RegistrationVolunteerService rvService;


    public static String TEST_URL = "apeiofjoiaejfoioijagrapioejfleiofja";  //gibberish so test won't pass by accident
    private RegistrationVolunteer v;

    @Before
    public void init() {
        v = new RegistrationVolunteer();
        v.setAdNumber("abc123");
    }


    @Test
    public void saveReturnsUrlGivenByMultiSafePayService() throws URISyntaxException {
        Mockito.when(mspService.getVolunteerPaymentUri(v, 6000)).thenReturn(TEST_URL);
        Mockito.when(rvService.save(v)).thenReturn(v);

        ResponseEntity<?> response = controller.save(v);
        Assert.assertSame(response.getHeaders().getFirst("Location"), TEST_URL);
    }
}
