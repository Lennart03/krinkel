package com.realdolmen.chiro.mvccontroller;

import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class PaymentWebControllerTest {
    @InjectMocks
    private PaymentWebController controller;

    @Mock
    private RegistrationParticipantService service;

    private final static String TEST_ORDER_ID = "abc123";

    @After
    public void verifyStrict(){
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    public void paymentSuccessCallsServiceToUpdateParticipantPaymentStatusWithOrderId() {
        controller.paymentSuccess(TEST_ORDER_ID);
        Mockito.verify(service, times(1)).updatePaymentStatus(TEST_ORDER_ID);
    }
}
