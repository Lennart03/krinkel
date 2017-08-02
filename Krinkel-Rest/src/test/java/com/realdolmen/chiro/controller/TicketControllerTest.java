package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.domain.payments.TicketPrice;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.payment.TicketDTO;
import com.realdolmen.chiro.payment.TicketPriceDTO;
import com.realdolmen.chiro.service.TicketService;
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

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketControllerTest extends MockMvcTest {


    @InjectMocks
    private TicketController ticketController;

    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    private final String adnumber = "987654";
    private final String street = "street";
    private final String houseNumber = "420";
    private final int postalCode = 666;
    private final String city = "city";
    private final String email = "jax.teller@soa.com";
    private final String firstName = "Jax";
    private final String lastName = "Teller";
    private final String stamnumber = "1";
    private final Gender gender = Gender.MAN;
    private final EventRole eventRole = EventRole.LEADER;
    private final Eatinghabbit eatinghabbit = Eatinghabbit.FISHANDMEAT;
    private final String emailSubscriber = "gemma.teller@soa.com";

    private List<TicketPrice> trainTicketPrices;
    private List<TicketPrice> foodTicketPrices;

    private final Integer ticketAmount = 1;
    private final Integer ticketAmount2 = 3;
    private final Integer ticketAmount3 = 9;
    private final BigDecimal price = new BigDecimal(10);
    private final BigDecimal price2 = new BigDecimal(15.5);
    private final BigDecimal price3 = new BigDecimal(20.35);
    private final BigDecimal transportationcosts = new BigDecimal(0.5);
    private final TicketType ticketTypeTrain = TicketType.TREIN;
    private final TicketType ticketTypeBon = TicketType.BON;

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

        participantWithAddress = new RegistrationParticipant(adnumber, email, firstName, lastName, new Date(), stamnumber, gender, eventRole, eatinghabbit, emailSubscriber);
        participantWithAddress.setAddress(new Address(street, houseNumber, postalCode, city));

        participantWithoutAddress = new RegistrationParticipant(adnumber, email, firstName, lastName, new Date(), stamnumber, gender, eventRole, eatinghabbit, emailSubscriber);
        participantWithoutAddress.setAddress(null);

        trainTicketPrices = new ArrayList<>();
        trainTicketPrices.add(new TicketPrice(ticketAmount, price, transportationcosts, ticketTypeTrain));

        foodTicketPrices = new ArrayList<>();
        foodTicketPrices.add(new TicketPrice(ticketAmount, price, transportationcosts, ticketTypeBon));
        foodTicketPrices.add(new TicketPrice(ticketAmount2, price2, transportationcosts, ticketTypeBon));
        foodTicketPrices.add(new TicketPrice(ticketAmount3, price3, transportationcosts, ticketTypeBon));
    }

    @Test
    public void getUserAddress() throws Exception {
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUserRegistered);
        Mockito.when(userService.getRegistrationParticipant(currentUserRegistered.getAdNumber())).thenReturn(participantWithAddress);
        ResponseEntity<?> entity = ticketController.getUserAddress();
        RegistrationParticipant participant = (RegistrationParticipant) entity.getBody();
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertEquals(adnumber, participant.getAdNumber());
        Assert.assertEquals(email, participant.getEmail());
        Assert.assertEquals(firstName, participant.getFirstName());
        Assert.assertEquals(lastName, participant.getLastName());
        Assert.assertEquals(stamnumber, participant.getStamnumber());
        Assert.assertEquals(gender, participant.getGender());
        Assert.assertEquals(eventRole, participant.getEventRole());
        Assert.assertEquals(eatinghabbit, participant.getEatinghabbit());
        Assert.assertEquals(email, participant.getEmailSubscriber());
        Assert.assertEquals(street, participant.getAddress().getStreet());
        Assert.assertEquals(houseNumber, participant.getAddress().getHouseNumber());
        Assert.assertEquals(postalCode, participant.getAddress().getPostalCode());
        Assert.assertEquals(city, participant.getAddress().getCity());
    }


    @Test
    public void getUserAddressWithInvalidUser() {
        Mockito.when(userService.getCurrentUser()).thenReturn(null);
        ResponseEntity<?> entity = ticketController.getUserAddress();
        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void getUserAddressWithRegistrationParticipantNoAddress() {
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

    @Test
    public void orderTicket() throws Exception {
        TicketDTO dto = new TicketDTO();
        Mockito.when(ticketService.createPayment(dto)).thenReturn("http://www.google.com");
        ResponseEntity<?> entity = ticketController.orderTicket(dto);
        Assert.assertEquals(new URI("http://www.google.com"), entity.getHeaders().getLocation());
        Assert.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    @Test
    public void orderTicketPaymentUrlIsNull() throws URISyntaxException {
        TicketDTO dto = new TicketDTO();
        Mockito.when(ticketService.createPayment(dto)).thenReturn(null);
        ResponseEntity<?> entity = ticketController.orderTicket(dto);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
    }

    @Test
    public void getTrainTicketPrice() throws Exception {
        Mockito.when(ticketService.getPricesForTickets(TicketType.TREIN)).thenReturn(trainTicketPrices);
        List<TicketPriceDTO> prices = ticketController.getTrainTicketPrices();
        Assert.assertEquals(1, prices.size());
        TicketPriceDTO ticketPrice = prices.get(0);
        Assert.assertEquals(price, new BigDecimal(ticketPrice.getPrice()));
        Assert.assertEquals(transportationcosts, new BigDecimal(ticketPrice.getTransportationCost()));
    }

    @Test
    public void getCouponPrices() throws Exception {
        Mockito.when(ticketService.getPricesForTickets(ticketTypeBon)).thenReturn(foodTicketPrices);
        List<TicketPriceDTO> prices = ticketController.getCouponPrices();
        Assert.assertEquals(3, prices.size());
        TicketPriceDTO ticketPrice = prices.get(1);
        Assert.assertEquals(price2, new BigDecimal(ticketPrice.getPrice()));
    }

    /**
     * This is a replica of the invalidUser method in the TicketController.
     * @param user
     * @return
     */
    private boolean invalidUser(User user) {
        return user == null || !user.getIsRegistered() || user.getAdNumber() == null || user.getAdNumber().length() == 0;
    }

}