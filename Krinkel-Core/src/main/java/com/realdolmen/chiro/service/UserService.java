package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.SecurityRole;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private ChiroUserAdapter adapter;

    @Autowired
    private RegistrationParticipantRepository repo;

    public User getUser(String adNumber) {
        User u = adapter.getChiroUser(adNumber);

        if (u != null) {
            RegistrationParticipant participant = repo.findByAdNumber(u.getAdNumber());

            if (participant != null) {
                u.setRegistered(true);

                if (participant.getStatus() == Status.PAID)
                    u.setHasPaid(true);
            }

            this.setSecurityRole(u);
        }

        return u;
    }

    private void setSecurityRole(User u) {
        if (u.getStamnummer() == null) return;
        if (u.getRole() != null && u.getRole() == SecurityRole.ADMIN) return;

        String stamNummer = u.getStamnummer();

        if (stamNummer.matches("NAT\\/0000")) {
          u.setRole(SecurityRole.NATIONAAL);
        }

        else if (stamNummer.matches("[A-Z]+ /0000")) {
            u.setRole(SecurityRole.VERBOND);
        }

        else if (stamNummer.matches("[A-Z]{3}/[0-9]{2}00") || stamNummer.matches("[A-Z]{2} /[0-9]{2}00")) {
            u.setRole(SecurityRole.GEWEST);
        }

        else {
            u.setRole(SecurityRole.GROEP);
        }

    }
}
