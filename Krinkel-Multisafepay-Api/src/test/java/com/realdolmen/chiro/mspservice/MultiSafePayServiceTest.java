package com.realdolmen.chiro.mspservice;


import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.sun.org.apache.xerces.internal.util.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.InvalidParameterException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultiSafePayService.class)
public class MultiSafePayServiceTest {

    @Autowired
    private MultiSafePayService multiSafePayService;

    private RegistrationParticipant p;


    @Before
    public void init() {
        p = new RegistrationParticipant("123", "jos@example.com", "Joske", "Vermeulen", new Date(), "AB 12/34", Gender.MAN, Role.ASPI, null);
    }


    @Test
    public void createPaymentReturnDtoWithUrl(){
        OrderDto payment = multiSafePayService.createPayment("abc", 100);
        Assert.assertNotNull(payment.getData().getPayment_url());
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenOrderIdIsNull() {
        multiSafePayService.createPayment(null, 100);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenAmountIsNull() {
        multiSafePayService.createPayment("abc", null);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenAmountIsNegative() {
        multiSafePayService.createPayment("abc", -1);
    }

    @Test
    public void getParticipantPaymentUriReturnsUri() {
        Assert.assertNotNull(multiSafePayService.getParticipantPaymentUri(p, 10000));
    }




}
