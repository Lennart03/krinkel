package com.realdolmen.chiro.mvccontroller;

import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PaymentWebControllerTest {
    @InjectMocks
    private PaymentWebController controller;

    @Mock
    private RegistrationParticipantService service;

    public final static String TEST_ORDER_ID = "abc123";


    @Test
    public void paymentSuccessCallsServiceToUpdateParticipantPaymentStatus() {
        controller.paymentSuccess(TEST_ORDER_ID);
        Mockito.verify(service, times(1)).updatePaymentStatus(TEST_ORDER_ID);
    }

}
