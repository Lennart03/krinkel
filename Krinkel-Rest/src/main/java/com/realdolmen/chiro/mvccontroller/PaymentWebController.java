package com.realdolmen.chiro.mvccontroller;

import com.realdolmen.chiro.domain.payments.PaymentStatus;
import com.realdolmen.chiro.service.BasketService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
import com.realdolmen.chiro.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This page is responsible for handling the callbacks used by the Multisafepay payment service.
 */
@Controller
@RequestMapping("/payment")
public class PaymentWebController {

    private Logger logger = LoggerFactory.getLogger(PaymentWebController.class);

    private BasketService basketService;
    private RegistrationParticipantService registrationParticipantService;
    private TicketService ticketService;

    @Autowired
    public PaymentWebController(BasketService basketService, RegistrationParticipantService registrationParticipantService, TicketService ticketService) {
        this.basketService = basketService;
        this.registrationParticipantService = registrationParticipantService;
        this.ticketService = ticketService;
    }

    /**
     * This is called when a payment from multisafepay is successful.
     *
     * @param orderId The ID of the order of multisafepay
     * @return Redirection
     */
    @RequestMapping(method = RequestMethod.GET, value = "/success")
    public String paymentSuccess(@RequestParam(name = "transactionid") String orderId) {
        logger.info("Payment Successful for Transaction " + orderId);
        if(orderId.contains("ticket")) {
            ticketService.updatePaymentStatus(orderId, PaymentStatus.SUCCESS);
        }
        if (!basketService.handleSuccessCallback(orderId))
            registrationParticipantService.updatePaymentStatus(orderId);

        return "redirect:/login";
    }

    /**
     * This is called when a payment from multisafepay failed.
     *
     * @param orderId The ID of the order of multisafepay
     * @return Redirection
     */
    @RequestMapping(method = RequestMethod.GET, value = "/failure")
    public String paymentFailure(@RequestParam(name = "transactionid") String orderId) {
        logger.info("Payment Failure for Transaction " + orderId);
        if(orderId.contains("ticket")) {
            ticketService.updatePaymentStatus(orderId, PaymentStatus.FAILED);
        }
        return "redirect:/site/index.html";
    }
}
