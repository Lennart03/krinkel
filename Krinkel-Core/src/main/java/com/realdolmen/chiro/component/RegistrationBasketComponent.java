package com.realdolmen.chiro.component;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class RegistrationBasketComponent {
    private List<RegistrationParticipant> usersInBasket = new ArrayList<>();

    public void addUserToBasket(RegistrationParticipant user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null!");
        }

        usersInBasket.add(user);
    }

    public List<RegistrationParticipant> getUsersInBasket() {
        return usersInBasket;
    }

    public void removeUserFromBasket(RegistrationParticipant user){
        if (user == null){
            throw new IllegalArgumentException("User cannot be null");
        }

        usersInBasket.remove(user);
    }

    public void reset() {
        usersInBasket.clear();
    }
}
