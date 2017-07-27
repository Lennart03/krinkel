package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.payments.Payment;
import com.realdolmen.chiro.domain.payments.TicketPrice;
import com.realdolmen.chiro.domain.payments.TicketType;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import com.realdolmen.chiro.payment.TicketDTO;
import com.realdolmen.chiro.repository.PaymentRepository;
import com.realdolmen.chiro.repository.TicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * This service is used to order tickets.
 */
@Service
public class TicketService {

    private MultiSafePayService multiSafePayService;
    private TicketPriceRepository ticketPriceRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public TicketService(MultiSafePayService multiSafePayService, TicketPriceRepository ticketPriceRepository, PaymentRepository paymentRepository) {
        this.multiSafePayService = multiSafePayService;
        this.ticketPriceRepository = ticketPriceRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Will call the MultiSafePay api to request a payment link according to the giving info of needed ticket.
     * @param ticketDTO Parameters to create the payment.
     * @return Payment url string.
     */
    public String createPayment(TicketDTO ticketDTO) {
        TicketPrice price;
        if(ticketDTO.getType() == TicketType.TREIN) {
            price = ticketPriceRepository.findByTicketType(TicketType.TREIN).get(0);
        } else {
            // For food/drink tickets the amount of tickets is important for the price.
            price = ticketPriceRepository.findByTicketTypeAndTicketAmount(ticketDTO.getType(), ticketDTO.getTicketAmount());
        }
        // This is the price of the ticket times the total times the ticket is ordered
        BigDecimal totalPrice = price.getPrice().multiply(new BigDecimal(ticketDTO.getTimesOrdered())).add(price.getTransportationcosts());
        Payment payment = new Payment(ticketDTO.getType(), totalPrice, ticketDTO.getFirstName(), ticketDTO.getLastName(), ticketDTO.getEmail(), ticketDTO.getAddress());
        payment = paymentRepository.save(payment);
        OrderDto orderDto = multiSafePayService.createPayment(payment);
        if(orderDto != null) {
            return orderDto.getData().getPayment_url();
        }
        return "";
    }

    public List<TicketPrice> getPricesForTickets() {
        return ticketPriceRepository.findAll();
    }

    public List<TicketPrice> getPricesForTickets(TicketType ticketType) {
        return ticketPriceRepository.findByTicketType(ticketType);
    }
}
