package com.realdolmen.chiro.component;

import com.realdolmen.chiro.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * This component is used only to store the currently logged in user. This was decoupled from UserService to soften the (theoretical, untested) impact on performance
 * that refactoring from Singleton to SessionScope should have.
 */
@Component
@SessionScope
public class CASCurrentlyLoggedInUserComponent {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
