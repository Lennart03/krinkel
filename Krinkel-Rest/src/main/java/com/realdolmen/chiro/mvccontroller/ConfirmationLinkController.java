package com.realdolmen.chiro.mvccontroller;

import com.realdolmen.chiro.service.ConfirmationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/confirmation")
public class ConfirmationLinkController {

    @Autowired
    private ConfirmationLinkService confirmationLinkService;

    /**
     * used when clicking the confirmation link in the email
     * @param adNumber
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String confirm(@RequestParam("ad") String adNumber, @RequestParam("token") String token, Model model){
        boolean success = confirmationLinkService.checkToken(adNumber, token);
        model.addAttribute("success", success);
        return "confirmation";
    }
}
