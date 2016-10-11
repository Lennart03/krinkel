package com.realdolmen.chiro.mvccontroller;

import com.realdolmen.chiro.service.RegistrationParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This page is reponsible for handling the callbacks used by the Multisafepay payment service.
 */
@Controller
@RequestMapping("/payment")
public class PaymentWebController {

    @Autowired
    private RegistrationParticipantService service;

    @RequestMapping(method = RequestMethod.GET, value = "/success")
    public String paymentSuccess(@RequestParam(name = "transactionid")String orderId) {
        System.out.println("reached payment succes for transaction " + orderId);
        service.updatePaymentStatus(orderId);
        return "redirect:/index.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/failure")
    public String paymentFailure(@RequestParam(name = "transactionid")String orderId) {
        System.out.println("reached payment failure for transaction" + orderId);

        return "redirect:/index.html";
    }
}
