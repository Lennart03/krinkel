package com.realdolmen.chiro.service;

import com.realdolmen.chiro.component.RegistrationBasketComponent;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class BasketService {

    @Autowired
    private UserService userService;

    @Autowired
    private MultiSafePayService multiSafePayService;

    @Autowired
    private RegistrationBasketComponent registrationBasketComponent;

    public void addUserToBasket(RegistrationParticipant user) {
        registrationBasketComponent.addUserToBasket(user);
    }

    public List<RegistrationParticipant> getUsersInBasket() {
        return registrationBasketComponent.getUsersInBasket();
    }

    public void removeUserFromBasket(RegistrationParticipant user) {
        registrationBasketComponent.removeUserFromBasket(user);
    }

    public String getRegistrationEmail() {
        if (userService.getCurrentUser() != null) {
            if (userService.getCurrentUser().getEmail() != null) {
                registrationBasketComponent.setDestinationMail(userService.getCurrentUser().getEmail());
            }
        }
        return registrationBasketComponent.getDestinationMail();
    }

    public void setRegistrationEmail(String mail) {
        registrationBasketComponent.setDestinationMail(mail);
        applySubscriberEmail();
    }

    public void applySubscriberEmail() {
        registrationBasketComponent.getUsersInBasket().forEach(u -> {
            if (!u.getEmailSubscriber().equalsIgnoreCase(registrationBasketComponent.getDestinationMail())) {
                u.setEmailSubscriber(registrationBasketComponent.getDestinationMail());
            }
        });
    }

    public void reset() {
        registrationBasketComponent.reset();
    }

    private Integer calculateTotalPrice() {
        return registrationBasketComponent.getUsersInBasket().stream().mapToInt(u -> 11000).sum();
    }

    public String getBasketPaymentUri() throws MultiSafePayService.InvalidPaymentOrderIdException {
        return multiSafePayService.getBasketPaymentUri(calculateTotalPrice(), userService.getCurrentUser());
    }
}
