package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.util.StamNumberTrimmer;
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

    @Autowired
    StamNumberTrimmer stamNumberTrimmer;

    public List<ChiroUnit> getVerbonden() {
        // Get the verbonden: the name + stamNummer
        List<ChiroUnit> verbonden = chiroUnitRepository.findAllVerbonden();
        // Set the participants and volunteers count
        for (ChiroUnit verbond : verbonden) {
            int countAllParticipants = chiroUnitRepository.countParticipantsByVerbond(verbond.getStamNummer());
            int countVolunteers = chiroUnitRepository.countVolunteersByVerbond(verbond.getStamNummer());
            System.err.println("stamnr: " + verbond.getStamNummer() + " #participants+volunteers: "
                    + countAllParticipants + ", #volunteers: " + countVolunteers);
            verbond.setParticipantsCount(countAllParticipants - countVolunteers);
            verbond.setVolunteersCount(countVolunteers);
            // Trim the stam nummers
            verbond.setStamNummer(stamNumberTrimmer.trim(verbond.getStamNummer()));
        }
        System.err.println("VERBONDEN LIST: " +verbonden);
        return verbonden;
    }

    public List<ChiroUnit> getGewesten(String verbondStamNummer){
        // first untrim the verbondStamNummer
        verbondStamNummer = stamNumberTrimmer.untrim(verbondStamNummer);

        // Get the gewesten: the name + stamNummer
        List<ChiroUnit> gewesten = chiroUnitRepository.findAllGewestenWhereVerbondStamNummerIs(verbondStamNummer);
        // Set the participants and volunteers count
        for (ChiroUnit gewest : gewesten) {
            System.err.println("Gewest stam nummer in getGewesten(): " + gewest.getStamNummer());
            int countAllParticipants = chiroUnitRepository.countParticipantsByGewest(gewest.getStamNummer());

            int countVolunteers = chiroUnitRepository.countVolunteersByGewest(gewest.getStamNummer());
            System.err.println("stamnr: " + gewest.getStamNummer() + " #participants+volunteers: "
                    + countAllParticipants + ", #volunteers: " + countVolunteers);
            gewest.setParticipantsCount(countAllParticipants - countVolunteers);
            gewest.setVolunteersCount(countVolunteers);
            // Trim the stam nummers.
            gewest.setStamNummer(stamNumberTrimmer.trim(gewest.getStamNummer()));
        }
        System.err.println("GEWESTEN LIST from verbond: " + verbondStamNummer + " -- " +gewesten);
        return gewesten;
    }

    public List<ChiroUnit> getGroepen(String gewestStamNummer){
        // first untrim the verbondStamNummer
        gewestStamNummer = stamNumberTrimmer.untrim(gewestStamNummer);
        // Get the gewesten: the name + stamNummer
        List<ChiroUnit> groepen = chiroUnitRepository.findAllGroepenWhereGewestStamNummerIs(gewestStamNummer);
        // Set the participants and volunteers count
        for (ChiroUnit groep : groepen) {
            int countAllParticipants = chiroUnitRepository.countParticipantsByGewest(groep.getStamNummer());
            int countVolunteers = chiroUnitRepository.countVolunteersByGewest(groep.getStamNummer());
            System.err.println("stamnr: " + groep.getStamNummer() + " #participants+volunteers: "
                    + countAllParticipants + ", #volunteers: " + countVolunteers);

            // Trim the stam nummers.
            groep.setStamNummer(stamNumberTrimmer.trim(groep.getStamNummer()));
        }
        System.err.println("GROEPEN LIST from gewest: " + gewestStamNummer + " -- " +groepen);
        return groepen;
    }
}
