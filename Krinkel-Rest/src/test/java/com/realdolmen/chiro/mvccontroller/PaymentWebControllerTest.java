package com.realdolmen.chiro.mvccontroller;

import com.realdolmen.chiro.service.BasketService;
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
    private PaymentWebController paymentWebController;

    @Mock
    private RegistrationParticipantService registrationParticipantService;

    @Mock
    private BasketService basketService;

    private final static String TEST_ORDER_ID = "abc123";

    @After
    public void verifyStrict(){
        Mockito.verifyNoMoreInteractions(registrationParticipantService);
    }

    @Test
    public void paymentSuccessCallsServiceToUpdateParticipantPaymentStatusWithOrderId() {
        paymentWebController.paymentSuccess(TEST_ORDER_ID);
        Mockito.verify(registrationParticipantService, times(1)).updatePaymentStatus(TEST_ORDER_ID);
    }

    @Test
    public void paymentFailureDoesNothingAtAll() {
        paymentWebController.paymentFailure(TEST_ORDER_ID);
    }
}
