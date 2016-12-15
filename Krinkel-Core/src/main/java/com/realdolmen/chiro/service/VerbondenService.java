package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by JCPBB69 on 15/12/2016.
 */
@Service
public class VerbondenService {

    @Autowired
    private ChiroUnitRepository chiroUnitRepository;

    private List<ChiroUnit> verbonden = new ArrayList<ChiroUnit>();

    @PostConstruct
    public void init(){
        verbonden = chiroUnitRepository.findAllVerbonden();
        System.err.println("VERBONDEN LIST: " +verbonden);
        for (ChiroUnit chiroUnit : verbonden) {
            int countAllParticipants = chiroUnitRepository.countParticipantsByVerbond(chiroUnit.getStamNummer());
            int countVolunteers = chiroUnitRepository.countVolunteersByVerbond(chiroUnit.getStamNummer());
            chiroUnit.setParticipantsCount(countAllParticipants - countVolunteers);
            chiroUnit.setVolunteersCount(countVolunteers);
        }
    }

    public List<ChiroUnit> getVerbonden() {
        return verbonden;
    }
}
