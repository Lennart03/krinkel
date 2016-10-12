package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Colleague;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.ColleagueRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColleagueService {

    @Autowired
    private ColleagueRepository repository;

    public List<Colleague> getColleagues(String adNumber) {
        return repository.findAllByAdNumber(adNumber);
    }
}
