package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.payments.Payment;
import com.realdolmen.chiro.domain.payments.PaymentStatus;
import com.realdolmen.chiro.domain.payments.TicketPrice;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.payment.TicketDTO;
import com.realdolmen.chiro.repository.PaymentRepository;
import com.realdolmen.chiro.repository.TicketPriceRepository;
import com.realdolmen.chiro.util.TicketPriceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * This service is used to order tickets.
 */
@Service
public class TicketService {

    private final static Logger logger = LoggerFactory.getLogger(TicketService.class);

    private MultiSafePayService multiSafePayService;
    private TicketPriceRepository ticketPriceRepository;
    private PaymentRepository paymentRepository;
    private TicketPriceCalculator ticketPriceCalculator;

    @Autowired
    public TicketService(MultiSafePayService multiSafePayService, TicketPriceRepository ticketPriceRepository, PaymentRepository paymentRepository, TicketPriceCalculator ticketPriceCalculator) {
        this.multiSafePayService = multiSafePayService;
        this.ticketPriceRepository = ticketPriceRepository;
        this.paymentRepository = paymentRepository;
        this.ticketPriceCalculator = ticketPriceCalculator;
    }

    /**
     * Will call the MultiSafePay api to request a payment link according to the giving info of needed ticket.
     * @param ticketDTO Parameters to create the payment.
     * @return Payment url string.
     */
    public String createPayment(TicketDTO ticketDTO) {
        logger.info("Creating payment for " + ticketDTO);
        TicketPrice price;
        if(ticketDTO.getType() == TicketType.TREIN) {
            price = ticketPriceRepository.findByTicketType(TicketType.TREIN).get(0);
        } else {
            // For food/drink tickets the amount of tickets is important for the price.
            price = ticketPriceRepository.findByTicketTypeAndTicketAmount(ticketDTO.getType(), ticketDTO.getTicketAmount());
        }
        // This is the price of the ticket times the total times the ticket is ordered
        BigDecimal totalPrice = ticketPriceCalculator.calculateTotalTicketPrice(price.getPrice(), price.getTicketAmount(), price.getTransportationcosts());
        Payment payment = new Payment(ticketDTO.getType(), totalPrice, ticketDTO.getFirstName(), ticketDTO.getLastName(), ticketDTO.getEmail(), ticketDTO.getPhoneNumber(), ticketDTO.getAddress());
        payment = paymentRepository.save(payment);
        OrderDto orderDto;
        try {
            orderDto = multiSafePayService.createPayment(payment);
            if(orderDto != null && orderDto.getData().getPayment_url() != null) {
                payment.setStatus(PaymentStatus.PENDING);
                paymentRepository.save(payment);
                return orderDto.getData().getPayment_url();
            }
            return null;
        } catch (MultiSafePayService.InvalidPaymentOrderIdException e) {
            logger.error("Payment error when creating payment url.", e);
            return null;
        }
    }

    public List<TicketPrice> getPricesForTickets() {
        return ticketPriceRepository.findAll();
    }

    public List<TicketPrice> getPricesForTickets(TicketType ticketType) {
        return ticketPriceRepository.findByTicketType(ticketType);
    }

    /**
     * Update the payment status of an order made by the multisafepay api
     * @param orderId Id of multisafepay payment.
     * @param paymentStatus {@link PaymentStatus} to which the status of the {@link Payment} entity will be updated.
     */
    public void updatePaymentStatus(String orderId, PaymentStatus paymentStatus) {
        if(orderId != null) {
            Integer paymentId = Integer.parseInt(orderId.split("-")[0]);
            Payment payment = paymentRepository.findOne(paymentId);
            payment.setStatus(paymentStatus);
            paymentRepository.save(payment);
        }
    }
}
