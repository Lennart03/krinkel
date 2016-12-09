package com.realdolmen.chiro.component;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.User;
import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class RegistrationBasketComponent {

    private List<RegistrationParticipant> usersInBasket = new ArrayList<>();

    @Email
    @NotNull
    private String destinationMail;

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

    public String getDestinationMail() {
        return destinationMail;
    }

    public void setDestinationMail(String destinationMail) {
        this.destinationMail = destinationMail;
    }

    public void reset() {
        usersInBasket.clear();
    }
}
