package com.realdolmen.chiro.mspservice;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.domain.payments.Payment;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Date;

public class MultiSafePayServiceTest extends SpringIntegrationTest {

    @Autowired
    private MultiSafePayService multiSafePayService;

    private RegistrationParticipant p;
    private RegistrationVolunteer v;
    private Address address;
    private User currentUser;

    private Payment payment;

    @Before
    public void setUp() {
        address = new Address("rttr", "3", 1600, "test");
        p = new RegistrationParticipant("123", "jos@example.com", "Joske", "Vermeulen", new Date(), "AB 12/34", Gender.MAN, EventRole.ASPI, null, "aster.deckers@example.org");
        p.setAddress(address);
        v = new RegistrationVolunteer();

        v.setAdNumber("1516");

        currentUser = new User();

        payment = new Payment(TicketType.TREIN, new BigDecimal(10.50), "Jax", "Teller", "jax.teller@soa.com" , "0457965132", address);
        payment.setId(1);
    }

    @Test
    public void createPaymentForTicketReturnsDtoWithUrl() throws MultiSafePayService.InvalidPaymentOrderIdException {
        OrderDto dto = multiSafePayService.createPayment(payment);
        Assert.assertNotNull(dto.getData().getPayment_url());
    }


    @Test
    public void createPaymentReturnsDtoWithUrl() throws MultiSafePayService.InvalidPaymentOrderIdException {
        OrderDto payment = multiSafePayService.createPayment(p, 100, currentUser);
        Assert.assertNotNull(payment.getData().getPayment_url());
    }

    @Test
    public void createPaymentForVolunteerWithEmptyFieldsReturnsDtoWithUrl() throws MultiSafePayService.InvalidPaymentOrderIdException {
        OrderDto payment = multiSafePayService.createPayment(v, 6000, currentUser);
        Assert.assertNotNull(payment.getData().getPayment_url());
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        multiSafePayService.createPayment(null);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setAddressBuyer(null);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentTypeIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setType(null);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentFirstNameBuyerIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setFirstNameBuyer(null);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentFirstNameBuyerIsEmpty() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setFirstNameBuyer("");
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentLastNameBuyerIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setLastNameBuyer(null);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentLastNameBuyerIsEmpty() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setLastNameBuyer("");
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentPhoneBuyerIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setPhoneNumberBuyer(null);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentPhoneBuyerIsEmpty() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setPhoneNumberBuyer("");
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressBuyerStreetIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        address.setStreet(null);
        payment.setAddressBuyer(address);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressBuyerStreetIsEmpty() throws MultiSafePayService.InvalidPaymentOrderIdException {
        address.setStreet("");
        payment.setAddressBuyer(address);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressBuyerHouseNumberIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        address.setHouseNumber(null);
        payment.setAddressBuyer(address);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressBuyerHouseNumberIsEmpty() throws MultiSafePayService.InvalidPaymentOrderIdException {
        address.setHouseNumber("");
        payment.setAddressBuyer(address);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressBuyerPostalCodeTooSmall() throws MultiSafePayService.InvalidPaymentOrderIdException {
        address.setPostalCode(0);
        payment.setAddressBuyer(address);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressBuyerPostalCodeTooLarge() throws MultiSafePayService.InvalidPaymentOrderIdException {
        address.setPostalCode(10000);
        payment.setAddressBuyer(address);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressBuyerCityIsEmpty() throws MultiSafePayService.InvalidPaymentOrderIdException {
        address.setCity("");
        payment.setAddressBuyer(address);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentAddressBuyerCityIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        address.setCity(null);
        payment.setAddressBuyer(address);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentEmailBuyerIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setEmailBuyer(null);
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentForTicketErrorWhenPaymentEmailBuyerIsEmpty() throws MultiSafePayService.InvalidPaymentOrderIdException {
        payment.setEmailBuyer("");
        multiSafePayService.createPayment(payment);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenOrderIdIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        p.setAdNumber(null);
        multiSafePayService.createPayment(p, 100, currentUser);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenAmountIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        multiSafePayService.createPayment(p, null, currentUser);
    }

    @Test(expected = InvalidParameterException.class)
    public void createPaymentThrowsErrorWhenAmountIsNegative() throws MultiSafePayService.InvalidPaymentOrderIdException {
        multiSafePayService.createPayment(p, -1, currentUser);
    }

    @Test
    public void getParticipantPaymentUriReturnsUri() throws MultiSafePayService.InvalidPaymentOrderIdException {
        Assert.assertNotNull(multiSafePayService.getParticipantPaymentUri(p, 10000, currentUser));
    }

    @Test
    public void getVolunteerPaymentUriReturnsUri() throws MultiSafePayService.InvalidPaymentOrderIdException {
        Assert.assertNotNull(multiSafePayService.getVolunteerPaymentUri(v, 6000, currentUser));
    }
}
