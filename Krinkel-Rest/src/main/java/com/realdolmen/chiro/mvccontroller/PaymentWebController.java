package com.realdolmen.chiro.mvccontroller;

import com.realdolmen.chiro.service.BasketService;
import com.realdolmen.chiro.service.RegistrationParticipantService;
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

    @Autowired
    private BasketService basketService;

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    /**
     * when payment completed successful
     *
     * @param orderId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/success")
    public String paymentSuccess(@RequestParam(name = "transactionid") String orderId) {
        logger.info("Payment Successful for Transaction " + orderId);
        if (!basketService.handleSuccessCallback(orderId))
            registrationParticipantService.updatePaymentStatus(orderId);

        return "redirect:/index.html";
    }

    /**
     * when payment not completed
     *
     * @param orderId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/failure")
    public String paymentFailure(@RequestParam(name = "transactionid") String orderId) {
        logger.info("Payment Failure for Transaction " + orderId);
        return "redirect:/site/index.html";
    }
}
