package com.realdolmen.chiro.mspservice;


import com.realdolmen.chiro.mspdto.OrderDto;
import com.sun.org.apache.xerces.internal.util.URI;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultiSafePayService.class)
public class MultiSafePayServiceTest {

    @Autowired
    private MultiSafePayService multiSafePayService;


    @Test
    public void createPaymentReturnDtoWithUrl() throws URI.MalformedURIException {
        OrderDto payment = multiSafePayService.createPayment();
        Assert.assertNotNull(payment.getPaymentUrl());
    }



}
