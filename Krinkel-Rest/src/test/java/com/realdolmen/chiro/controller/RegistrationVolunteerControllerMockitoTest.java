package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.service.RegistrationVolunteerService;
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
    private RegistrationVolunteerController controller;

    @Mock
    private MultiSafePayService mspService;

    @Mock
    private RegistrationVolunteerService rvService;

    private String TEST_URL = "apeiofjoiaejfoioijagrapioejfleiofja";  //gibberish so test won't pass by accident
    private RegistrationVolunteer v;

    @Before
    public void setUp() {
        v = new RegistrationVolunteer();
        v.setAdNumber("abc123");
    }

    @After
    public void verifyStrict(){
        Mockito.verifyNoMoreInteractions(mspService);
        Mockito.verifyNoMoreInteractions(rvService);
    }

    @Test
    public void saveReturnsUrlGivenByMultiSafePayService() throws URISyntaxException, MultiSafePayService.InvalidPaymentOrderIdException {
        Mockito.when(mspService.getVolunteerPaymentUri(v, 6000)).thenReturn(TEST_URL);
        Mockito.when(rvService.save(v)).thenReturn(v);

        ResponseEntity<?> response = controller.save(v);
        Assert.assertSame(response.getHeaders().getFirst("Location"), TEST_URL);

        Mockito.verify(mspService).getVolunteerPaymentUri(v, 6000);
        Mockito.verify(rvService).save(v);
    }
}
