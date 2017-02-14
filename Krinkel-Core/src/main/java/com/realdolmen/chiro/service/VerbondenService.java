package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.Status;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.util.StamNumberTrimmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
// Security
    /*@PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToFindVerbonden()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeVerbonden(filterObject)")*/
    public List<ChiroUnit> getVerbonden() {
        // Get the verbonden: the name + stamNummer
        List<ChiroUnit> verbonden = chiroUnitRepository.findAllVerbonden();
        // Set the participants and volunteers count
        for (ChiroUnit verbond : verbonden) {
            int countAllParticipants = chiroUnitRepository.countParticipantsByVerbond(verbond.getStamNummer());
            int countVolunteers = chiroUnitRepository.countVolunteersByVerbond(verbond.getStamNummer());
//            System.err.println("stamnr: " + verbond.getStamNummer() + " #participants+volunteers: "
//                    + countAllParticipants + ", #volunteers: " + countVolunteers);
            verbond.setParticipantsCount(countAllParticipants - countVolunteers);
            verbond.setVolunteersCount(countVolunteers);

            int countAllParticipantsPaid = chiroUnitRepository.countParticipantsByVerbond(verbond.getStamNummer(), Status.PAID);
            int countVolunteersPaid = chiroUnitRepository.countVolunteersByVerbond(verbond.getStamNummer(), Status.PAID);
            verbond.setParticipantsCountPaid(countAllParticipantsPaid - countVolunteersPaid);
            verbond.setVolunteersCountPaid(countVolunteersPaid);

            // Trim the stam nummers
            verbond.setStamNummer(stamNumberTrimmer.trim(verbond.getStamNummer()));
        }
//        System.err.println("VERBONDEN LIST: " +verbonden);
        return verbonden;
    }
    // Security
    /*@PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToFindUnits()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeUnits(filterObject)")*/
    public List<ChiroUnit> getGewesten(String verbondStamNummer){
//        System.err.println("Hi from getGewesten");
        // first get the untrimmed verbondStamNummer for searching the DB
        String unTimmedverbondStamNummer = stamNumberTrimmer.untrim(verbondStamNummer);

        // Get the gewesten: the name + stamNummer
        List<ChiroUnit> gewesten = chiroUnitRepository.findAllGewestenWhereVerbondStamNummerIs(unTimmedverbondStamNummer);
        // Set the participants and volunteers count
        for (ChiroUnit gewest : gewesten) {
//            System.err.println("Gewest stam nummer in getGewesten(): " + gewest.getStamNummer());
            int countAllParticipants = chiroUnitRepository.countParticipantsByGewest(gewest.getStamNummer());

            int countVolunteers = chiroUnitRepository.countVolunteersByGewest(gewest.getStamNummer());
//            System.err.println("stamnr: " + gewest.getStamNummer() + " #participants+volunteers: "
//                    + countAllParticipants + ", #volunteers: " + countVolunteers);
            gewest.setParticipantsCount(countAllParticipants - countVolunteers);
            gewest.setVolunteersCount(countVolunteers);
            // Trim the stam nummers.
            gewest.setStamNummer(stamNumberTrimmer.trim(gewest.getStamNummer()));
            // Set "upper" to verbondStamNummer => used for security filtering (name may remain empty, it is not used for filtering)
            gewest.setUpper(new ChiroUnit(verbondStamNummer, ""));
        }
//        System.err.println("GEWESTEN LIST from verbond: " + verbondStamNummer + " -- " +gewesten);
        return gewesten;
    }
    // Security
    /*@PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToFindUnits()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeUnits(filterObject)")*/
    public List<ChiroUnit> getGroepen(String gewestStamNummer){
//        System.err.println("Hi from getGroepen");
        // first get the untrimed verbondStamNummer for searching inside the DB
        String untrimmedGewestStamNummer = stamNumberTrimmer.untrim(gewestStamNummer);
        // Get the gewesten: the name + stamNummer
        List<ChiroUnit> groepen = chiroUnitRepository.findAllGroepenWhereGewestStamNummerIs(untrimmedGewestStamNummer);
        // Set the participants and volunteers count
        for (ChiroUnit groep : groepen) {
            int countAllParticipants = chiroUnitRepository.countParticipantsByGroep(groep.getStamNummer());
            int countVolunteers = chiroUnitRepository.countVolunteersByGroep(groep.getStamNummer());
//            System.err.println("stamnr: " + groep.getStamNummer() + " #participants+volunteers: "
//                    + countAllParticipants + ", #volunteers: " + countVolunteers);
            groep.setParticipantsCount(countAllParticipants - countVolunteers);
            groep.setVolunteersCount(countVolunteers);

            int countAllParticipantsPaid = chiroUnitRepository.countParticipantsByGroep(groep.getStamNummer(), Status.PAID);
            int countVolunteersPaid = chiroUnitRepository.countVolunteersByGroep(groep.getStamNummer(), Status.PAID);
            groep.setParticipantsCountPaid(countAllParticipantsPaid - countVolunteersPaid);
            groep.setVolunteersCountPaid(countVolunteersPaid);
            // Trim the stam nummers.
            groep.setStamNummer(stamNumberTrimmer.trim(groep.getStamNummer()));
            // Set "upper" to gewestStamNummer => used for security filtering (name may remain empty, it is not used for filtering)
            groep.setUpper(new ChiroUnit(gewestStamNummer, ""));
        }
//        System.err.println("GROEPEN LIST from gewest: " + gewestStamNummer + " -- " +groepen);
        return groepen;
    }
    // Security
    /*@PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToGetParticipants()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeParticipants(filterObject)")*/
    public List<RegistrationParticipant> getRegistrationParticipants(String groepStamNummer){
//        System.err.println("Hi from getRegistrationParticipants");
        // first untrim the groepStamNummer
        groepStamNummer = stamNumberTrimmer.untrim(groepStamNummer);

        List<RegistrationParticipant> registrationParticipants = chiroUnitRepository.returnParticipantsByGroep(groepStamNummer);

        List<RegistrationParticipant> registrationParticipantsWithoutVolunteers = new ArrayList<>();

        for (RegistrationParticipant registrationParticipant : registrationParticipants) {
            if(! (registrationParticipant instanceof RegistrationVolunteer) ){
                registrationParticipantsWithoutVolunteers.add(registrationParticipant);
            }
        }
//        System.err.println("Nr of participants in getRegistrationParticipants verbondenservice: " +registrationParticipantsWithoutVolunteers.size());
        return registrationParticipantsWithoutVolunteers;
    }
    // Security
    /*@PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToGetVolunteers()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeVolunteers(filterObject)")*/
    public List<RegistrationVolunteer> getRegistrationVolunteers(String groepStamNummer){
//        System.err.println("Hi from getRegistrationVolunteers");
        // first untrim the groepStamNummer
        groepStamNummer = stamNumberTrimmer.untrim(groepStamNummer);

        return chiroUnitRepository.returnVolunteersByGroep(groepStamNummer);
    }

}
