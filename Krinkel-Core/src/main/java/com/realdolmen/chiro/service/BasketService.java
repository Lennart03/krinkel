package com.realdolmen.chiro.service;

import com.realdolmen.chiro.component.RegistrationBasketComponent;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.dto.UserDTO;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.mspservice.MultiSafePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    @Autowired
    private UserService userService;

    @Autowired
    private MultiSafePayService multiSafePayService;

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @Autowired
    private RegistrationBasketComponent registrationBasketComponent;

    @PreAuthorize("@UserServiceSecurity.hasPermissionToGetColleagues()")
    public void addUserToBasket(RegistrationParticipant user) {
        Optional<RegistrationParticipant> rp = registrationBasketComponent.getUsersInBasket().stream().filter(u -> u.getAdNumber().equals(user.getAdNumber())).findAny();
        if (rp.isPresent()) {
            throw new IllegalArgumentException("User already in basket");
        }
        registrationBasketComponent.addUserToBasket(user);
    }

    @PreAuthorize("@UserServiceSecurity.hasPermissionToGetColleagues()")
    public List<RegistrationParticipant> getUsersInBasket() {
        return registrationBasketComponent.getUsersInBasket();
    }

    @PreAuthorize("@UserServiceSecurity.hasPermissionToGetColleagues()")
    public void removeUserFromBasket(RegistrationParticipant user) {
        registrationBasketComponent.removeUserFromBasket(user);
    }

    @PreAuthorize("@UserServiceSecurity.hasPermissionToGetColleagues()")
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


    @PreAuthorize("@UserServiceSecurity.hasPermissionToGetColleagues()")
    public String initializePayment() throws MultiSafePayService.InvalidPaymentOrderIdException {
        applySubscriberEmail();
        registrationBasketComponent.getUsersInBasket().forEach(u -> {
            u.setStatus(Status.TO_BE_PAID);
            registrationParticipantService.save(u);
        });

        String url = getBasketPaymentUri(registrationBasketComponent.getDestinationMail());
        return url;
    }

    private String getBasketPaymentUri(String buyerEmail) throws MultiSafePayService.InvalidPaymentOrderIdException {
        UserDTO userDTO = new UserDTO(userService.getCurrentUser());
        userDTO.setEmail(buyerEmail);
        OrderDto order = multiSafePayService.createPayment(calculateTotalPrice(), userDTO);
        registrationBasketComponent.setOrder(order);
        return order.getData().getPayment_url();
    }

    public boolean handleSuccessCallback(String orderId) {
        if (multiSafePayService.orderIsPaid(orderId)) {
            if (registrationBasketComponent.getOrder() != null && orderId.equals(registrationBasketComponent.getOrder().getData().getOrder_id())) {
                registrationBasketComponent.getUsersInBasket().forEach(u -> {
                    u.setStatus(Status.PAID);
                    registrationParticipantService.save(u);
                    registrationParticipantService.createRegistrationCommunication(u);
                });
                reset();
                return true;
            }
        }

        return false;
    }
}
