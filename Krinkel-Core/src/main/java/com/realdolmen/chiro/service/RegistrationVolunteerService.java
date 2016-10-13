package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationVolunteerService {
    public final static Integer PRICE_IN_EUROCENTS = 6000;

    @Autowired
    private RegistrationVolunteerRepository repository;

    @Autowired
    private ChiroUserAdapter adapter;

    public RegistrationVolunteer save(RegistrationVolunteer registration) {
        User chiroUser = adapter.getChiroUser(registration.getAdNumber());
        if(repository.findByAdNumber(registration.getAdNumber()) == null && chiroUser != null) {
            String stamnummer = chiroUser.getStamnummer();
            registration.setStamnumber(stamnummer);
            return repository.save(registration);
        }
        return null;
    }
}
