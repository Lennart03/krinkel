package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.TestConfig;
import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.payments.Payment;
import com.realdolmen.chiro.domain.payments.TicketPrice;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.payment.TicketDTO;
import com.realdolmen.chiro.repository.PaymentRepository;
import com.realdolmen.chiro.repository.TicketPriceRepository;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import com.realdolmen.chiro.util.TicketPriceCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = {TestConfig.class})
public class TicketServiceTest extends SpringIntegrationTest {

    private final String street = "Elmstraat";
    private final String housenumber = "666";
    private final String city = "ciry";
    private final String firstName = "Jax";
    private final String lastName = "Teller";
    private final String email = "jax.teller@soa.com";
    private final String phone = "0458976453";

    private final Integer postalCode = 1111;
    private final Integer ticketAmount = 1;
    private final Integer timesOrdered = 1;

    private final Address address = new Address(street, housenumber, postalCode, city);

    private TicketDTO dto;

    @Autowired
    private TicketService ticketService;

    @InjectMocks
    private TicketService mockedTicketService;

    @Mock
    private TicketPriceRepository ticketPriceRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private TicketPriceCalculator ticketPriceCalculator;

    @Mock
    private MultiSafePayService multiSafePayService;

    @Before
    public void setup() {
        dto = new TicketDTO();
        dto.setType(TicketType.TREIN);
        dto.setTicketAmount(ticketAmount);
        dto.setTimesOrdered(timesOrdered);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setPhoneNumber(phone);
        dto.setAddress(address);
    }

    @Test
    public void getPricesForTickets() throws Exception {
        List<TicketPrice> prices = ticketService.getPricesForTickets();
        Assert.assertEquals(7, prices.size());
        TicketPrice price = prices.get(0);
        Assert.assertEquals(TicketType.TREIN, price.getTicketType());
        Assert.assertEquals(new BigDecimal("11.50"), price.getPrice());
        Assert.assertEquals(new BigDecimal(0.50), price.getTransportationcosts());
        price = prices.get(3);
        Assert.assertEquals(TicketType.BON, price.getTicketType());
    }

    @Test
    public void getPricesForTicketsWithType() throws Exception {
        List<TicketPrice> pricesForTrain = ticketService.getPricesForTickets(TicketType.TREIN);
        Assert.assertEquals(1, pricesForTrain.size());
        TicketPrice price = pricesForTrain.get(0);
        Assert.assertEquals(TicketType.TREIN, price.getTicketType());
        Assert.assertEquals(new BigDecimal("11.50"), price.getPrice());
        Assert.assertEquals(new BigDecimal(0.50), price.getTransportationcosts());
        List<TicketPrice> pricesForCoupons = ticketService.getPricesForTickets(TicketType.BON);
        Assert.assertEquals(6, pricesForCoupons.size());
        price = pricesForCoupons.get(0);
        Assert.assertEquals(TicketType.BON, price.getTicketType());
    }

    @Test
    public void createPayment() throws Exception {
        String paymentUrl = ticketService.createPayment(dto);
        Assert.assertNotNull(paymentUrl);
        System.err.println(paymentUrl);
        Assert.assertNotNull(paymentUrl);
    }

    @Test
    public void createPaymentOrderDTOIsNull() throws MultiSafePayService.InvalidPaymentOrderIdException {
        Payment payment = new Payment(dto.getType(), new BigDecimal(12.0), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), dto.getAddress());
        List<TicketPrice> ticketPrices = new ArrayList<>();
        ticketPrices.add(new TicketPrice(1, new BigDecimal(11.50), new BigDecimal(0.50), TicketType.TREIN));
        Mockito.when(multiSafePayService.createPayment(payment)).thenReturn(null);
        Mockito.when(ticketPriceRepository.findByTicketType(dto.getType())).thenReturn(ticketPrices);
        Mockito.when(ticketPriceCalculator.calculateTotalTicketPrice(new BigDecimal(11.50), 1, new BigDecimal(0.50))).thenReturn(new BigDecimal(12.0));
        Mockito.when(paymentRepository.save(payment)).thenReturn(payment);
        String paymentUrl = mockedTicketService.createPayment(dto);
        Assert.assertNull(paymentUrl);
    }

}