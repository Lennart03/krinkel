package com.realdolmen.chiro.mvccontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This page is reponsible for handling the callbacks used by the Multisafepay payment service.
 */
@Controller
@RequestMapping("/payment")
public class PaymentWebController {

    @RequestMapping(method = RequestMethod.GET, value = "/success")
    public String paymentSuccess() {
        return "redirect:/index.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/failure")
    public String paymentFailure() {
        return "redirect:/index.html";
    }
}
