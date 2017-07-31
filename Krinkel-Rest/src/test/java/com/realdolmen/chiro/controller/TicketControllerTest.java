package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.service.UserService;
import com.realdolmen.chiro.spring_test.MockMvcTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TicketControllerTest extends MockMvcTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private UserService userService;

    private final String adnumber = "987654";
    private final String street = "street";
    private final String houseNumber = "420";
    private final int postalCode = 666;
    private final String city = "city";

    private User currentUserRegistered;
    private User currentUserNotRegistered;

    private RegistrationParticipant participantWithAddress;
    private RegistrationParticipant participantWithoutAddress;

    @Before
    public void setUp() {
        currentUserRegistered = new User();
        currentUserRegistered.setAdNumber(adnumber);
        currentUserRegistered.setRegistered(true);

        currentUserNotRegistered = new User();
        currentUserNotRegistered.setAdNumber(adnumber);
        currentUserNotRegistered.setRegistered(false);

        participantWithAddress = new RegistrationParticipant();
        participantWithAddress.setAddress(new Address(street, houseNumber, postalCode, city));

        participantWithoutAddress = new RegistrationParticipant();
        participantWithoutAddress.setAddress(null);
    }

    @Test
    public void getUserAddress() throws Exception {
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUserRegistered);
        Mockito.when(userService.getRegistrationParticipant(currentUserRegistered.getAdNumber())).thenReturn(participantWithAddress);
        ResponseEntity<?> entity = ticketController.getUserAddress();
        Address address = (Address) entity.getBody();
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertEquals(street, address.getStreet());
        Assert.assertEquals(houseNumber, address.getHouseNumber());
        Assert.assertEquals(postalCode, address.getPostalCode());
        Assert.assertEquals(city, address.getCity());
    }

    @Test
    public void getUserAddressNonRegisteredUser(){
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUserNotRegistered);
        Mockito.when(userService.getRegistrationParticipant(currentUserRegistered.getAdNumber())).thenReturn(participantWithAddress);

    }

    @Test
    public void getUserAddressWithInvalidUser() {
        Mockito.when(userService.getCurrentUser()).thenReturn(null);
        ResponseEntity<?> entity = ticketController.getUserAddress();
        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void getUsetAddresWithRegistrationParticipantNoAddress() {
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUserRegistered);
        Mockito.when(userService.getRegistrationParticipant(currentUserRegistered.getAdNumber())).thenReturn(participantWithoutAddress);
        ResponseEntity<?> entity = ticketController.getUserAddress();
        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void testInvalidUserUserNull() {
        Assert.assertEquals(true, invalidUser(null));
    }

    @Test
    public void testInvalidUserNotRegistered() {
        Assert.assertEquals(true, invalidUser(currentUserNotRegistered));
    }

    @Test
    public void testInvalidUserNoAdNumber() {
        currentUserRegistered.setAdNumber(null);
        Assert.assertEquals(true, invalidUser(currentUserRegistered));
    }

    @Test
    public void testInvalidUserAdnumberEmpty() {
        currentUserRegistered.setAdNumber("");
        Assert.assertEquals(true, invalidUser(currentUserRegistered));
    }

    @Test
    public void testValidUser() {
        Assert.assertEquals(false, invalidUser(currentUserRegistered));
    }


    private boolean invalidUser(User user) {
        return user == null || !user.getIsRegistered() || user.getAdNumber() == null || user.getAdNumber().length() == 0;
    }

}