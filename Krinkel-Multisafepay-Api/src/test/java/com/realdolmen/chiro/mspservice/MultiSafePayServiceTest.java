package com.realdolmen.chiro.mspservice;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
import java.util.Date;

public class MultiSafePayServiceTest extends SpringIntegrationTest {

    @Autowired
    private MultiSafePayService multiSafePayService;

    private RegistrationParticipant p;
    private RegistrationVolunteer v;
    private Address address;

    @Before
    public void setUp() {
        address = new Address("rttr", "3", 1600, "test");
        p = new RegistrationParticipant("123", "jos@example.com", "Joske", "Vermeulen", new Date(), "AB 12/34", Gender.MAN, EventRole.ASPI, null);
        p.setAddress(address);
        v = new RegistrationVolunteer();

        v.setAdNumber("1516");
    }


    @Test
    public void createPaymentReturnsDtoWithUrl() throws MultiSafePayService.InvalidPaymentOrderIdException {
        OrderDto payment = multiSafePayService.createPayment(p, 100);
        Assert.assertNotNull(payment.getData().getPayment_url());
    }

    @Test
    public void createPaymentForVolunteerWithEmptyFieldsReturnsDtoWithUrl() throws MultiSafePayService.InvalidPaymentOrderIdException {
        OrderDto payment = multiSafePayService.createPayment(v, 6000);
        Assert.assertNotNull(payment.getData().getPayment_url());
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenOrderIdIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        p.setAdNumber(null);
        multiSafePayService.createPayment(p, 100);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenAmountIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        multiSafePayService.createPayment(p, null);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenAmountIsNegative() throws MultiSafePayService.InvalidPaymentOrderIdException {
        multiSafePayService.createPayment(p, -1);
    }

    @Test
    public void getParticipantPaymentUriReturnsUri() throws MultiSafePayService.InvalidPaymentOrderIdException {
        Assert.assertNotNull(multiSafePayService.getParticipantPaymentUri(p, 10000));
    }

    @Test
    public void getVolunteerPaymentUriReturnsUri() throws MultiSafePayService.InvalidPaymentOrderIdException {
        Assert.assertNotNull(multiSafePayService.getVolunteerPaymentUri(v, 6000));
    }
}
