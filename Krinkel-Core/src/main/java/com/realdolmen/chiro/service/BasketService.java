package com.realdolmen.chiro.service;

import com.realdolmen.chiro.component.RegistrationBasketComponent;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationBasketComponent registrationBasketComponent;

    public void addUserToBasket(RegistrationParticipant user) {
        registrationBasketComponent.addUserToBasket(user);
    }

    public List<RegistrationParticipant> getUsersInBasket() {
        //check security
       return registrationBasketComponent.getUsersInBasket();
    }

    public void removeUserFromBasket(RegistrationParticipant user){
        //check security
        registrationBasketComponent.removeUserFromBasket(user);
    }

    public void reset() {
        //check security
        registrationBasketComponent.reset();
    }

}
