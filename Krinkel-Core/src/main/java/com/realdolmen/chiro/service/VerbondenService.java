package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.CampGround;
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
//    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToFindVerbonden()")
//    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeVerbonden(filterObject)")
    public List<ChiroUnit> getVerbonden() {
        // Get the verbonden: the name + stamNummer => gets you the 10 normal verbonden
        List<ChiroUnit> verbonden = chiroUnitRepository.findAllVerbonden();
        // Add the "special" verbonden Others, Nationale kampgrond and Internationale kampgrond
// THESE ARE NOW IN THE .SQL
//        verbonden.add(new ChiroUnit("NAT", "Nationale kampgrond"));
//        verbonden.add(new ChiroUnit("INT", "Internationale kamgrond"));

        // Set the participants count based on VERBOND
        for (ChiroUnit verbond : verbonden) {
            // ALL PARTICIPANTS INSIDE VERBOND => NOT USED ANYMORE
            //int countAllParticipants = chiroUnitRepository.countParticipantsByVerbond(verbond.getStamNummer());
            //int countVolunteers = chiroUnitRepository.countVolunteersByVerbond(verbond.getStamNummer());
//            System.err.println("stamnr: " + verbond.getStamNummer() + " #participants+volunteers: "
//                    + countAllParticipants + ", #volunteers: " + countVolunteers);
            //verbond.setParticipantsCount(countAllParticipants - countVolunteers);

            // if internationaal => enkel buddy = true tellen anders enkel buddy = false tellen
            boolean internationaal = checkForInternational(verbond.getStamNummer());

            if(internationaal) {
                verbond.setParticipantsCountPaid(chiroUnitRepository.countParticipantsInternationaal(Status.PAID));
                verbond.setParticipantsCountConfirmed(chiroUnitRepository.countParticipantsInternationaal(Status.CONFIRMED));
                verbond.setParticipantsCountUnpaid(chiroUnitRepository.countParticipantsInternationaal(Status.TO_BE_PAID));
                verbond.setParticipantsCountCancelled(chiroUnitRepository.countParticipantsInternationaal(Status.CANCELLED));
            } else{
                verbond.setParticipantsCountPaid(getCountsParticipantsOnlyVerbond(verbond.getStamNummer(), Status.PAID, internationaal));
                verbond.setParticipantsCountConfirmed(getCountsParticipantsOnlyVerbond(verbond.getStamNummer(), Status.CONFIRMED, internationaal));
                verbond.setParticipantsCountUnpaid(getCountsParticipantsOnlyVerbond(verbond.getStamNummer(), Status.TO_BE_PAID, internationaal));
                verbond.setParticipantsCountCancelled(getCountsParticipantsOnlyVerbond(verbond.getStamNummer(), Status.CANCELLED, internationaal));
            }
            // Set the volunteers count based on CAMPGROUND
            verbond.setVolunteersCountConfirmed(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(verbond.getName()), Status.CONFIRMED));
            verbond.setVolunteersCountPaid(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(verbond.getName()), Status.PAID));
            verbond.setVolunteersCountUnpaid(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(verbond.getName()), Status.TO_BE_PAID));
            verbond.setVolunteersCountCancelled(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(verbond.getName()), Status.CANCELLED));

            // Trim the stam nummers
            verbond.setStamNummer(stamNumberTrimmer.trim(verbond.getStamNummer()));
        }

//        System.err.println("VERBONDEN LIST: " +verbonden);
        return verbonden;
    }

    private int getCountsParticipantsOnlyVerbond(String stamNummer, Status status, boolean countBuddies){
        int countAllParticipantsStatus = chiroUnitRepository.countParticipantsByVerbond(stamNummer, status, countBuddies);
        int countVolunteersStatus = chiroUnitRepository.countVolunteersByVerbond(stamNummer, status, countBuddies);
        return countAllParticipantsStatus - countVolunteersStatus;
    }

    private boolean checkForInternational(String stamNummer){
        boolean internationaal;
        if(stamNummer.equals("INT")) {
            internationaal = true;
            System.err.println("Internationaal is true " + stamNummer);
        } else {
            internationaal = false;
            System.err.println("Internationaal is false " + stamNummer);
        }
        return internationaal;
    }

    // Security
//    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToFindUnits()")
//    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeUnits(filterObject)")
    public List<ChiroUnit> getGewesten(String verbondStamNummer){
//        System.err.println("Hi from getGewesten");
        // first get the untrimmed verbondStamNummer for searching the DB
        String unTimmedverbondStamNummer = stamNumberTrimmer.untrim(verbondStamNummer);

        // Get the gewesten: the name + stamNummer
        List<ChiroUnit> gewesten = chiroUnitRepository.findAllGewestenWhereVerbondStamNummerIs(unTimmedverbondStamNummer);
        // Set the participants and volunteers count
        System.out.println("Gewesten in verbond "+verbondStamNummer+": ");
        for (ChiroUnit gewest : gewesten) {
            // if internationaal => enkel buddy = true tellen anders enkel buddy = false tellen

            System.out.println(gewest.getName());
            boolean internationaal = checkForInternational(gewest.getStamNummer());

            if(internationaal) {
                gewest.setParticipantsCountPaid(chiroUnitRepository.countParticipantsInternationaal(Status.PAID));
                gewest.setParticipantsCountConfirmed(chiroUnitRepository.countParticipantsInternationaal(Status.CONFIRMED));
                gewest.setParticipantsCountUnpaid(chiroUnitRepository.countParticipantsInternationaal(Status.TO_BE_PAID));
                gewest.setParticipantsCountCancelled(chiroUnitRepository.countParticipantsInternationaal(Status.CANCELLED));
            } else {
                gewest.setParticipantsCountPaid(getCountsParticipantsOnlyGewest(gewest.getStamNummer(), Status.PAID, internationaal));
                gewest.setParticipantsCountConfirmed(getCountsParticipantsOnlyGewest(gewest.getStamNummer(), Status.CONFIRMED, internationaal));
                gewest.setParticipantsCountUnpaid(getCountsParticipantsOnlyGewest(gewest.getStamNummer(), Status.TO_BE_PAID, internationaal));
                gewest.setParticipantsCountCancelled(getCountsParticipantsOnlyGewest(gewest.getStamNummer(), Status.CANCELLED, internationaal));
            }
            // Set the volunteers count based on CAMPGROUND
//            gewest.setVolunteersCountConfirmed(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(gewest.getName()), Status.CONFIRMED));
//            gewest.setVolunteersCountPaid(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(gewest.getName()), Status.PAID));
//            gewest.setVolunteersCountUnpaid(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(gewest.getName()), Status.TO_BE_PAID));
//            gewest.setVolunteersCountCancelled(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(gewest.getName()), Status.CANCELLED));


//            System.err.println("Gewest stam nummer in getGewesten(): " + gewest.getStamNummer());
//            int countAllParticipants = chiroUnitRepository.countParticipantsByGewest(gewest.getStamNummer());
//
//            int countVolunteers = chiroUnitRepository.countVolunteersByGewest(gewest.getStamNummer());
////            System.err.println("stamnr: " + gewest.getStamNummer() + " #participants+volunteers: "
////                    + countAllParticipants + ", #volunteers: " + countVolunteers);
//            gewest.setParticipantsCount(countAllParticipants - countVolunteers);
//            gewest.setVolunteersCount(countVolunteers);

            // Trim the stam nummers.
            gewest.setStamNummer(stamNumberTrimmer.trim(gewest.getStamNummer()));
            // Set "upper" to verbondStamNummer => used for security filtering (name may remain empty, it is not used for filtering)
            gewest.setUpper(new ChiroUnit(verbondStamNummer, ""));
        }
//        System.err.println("GEWESTEN LIST from verbond: " + verbondStamNummer + " -- " +gewesten);
        return gewesten;
    }

    private int getCountsParticipantsOnlyGewest(String stamNummer, Status status, boolean countBuddies){
        int countAllParticipantsStatus = chiroUnitRepository.countParticipantsByGewest(stamNummer, status, countBuddies);
        int countVolunteersStatus = chiroUnitRepository.countVolunteersByGewest(stamNummer, status, countBuddies);
        return countAllParticipantsStatus - countVolunteersStatus;
    }

    // Security
//    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToFindUnits()")
//    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeUnits(filterObject)")
    public List<ChiroUnit> getGroepen(String gewestStamNummer){
//        System.err.println("Hi from getGroepen");
        // first get the untrimed verbondStamNummer for searching inside the DB
        String untrimmedGewestStamNummer = stamNumberTrimmer.untrim(gewestStamNummer);
        // Get the gewesten: the name + stamNummer
        List<ChiroUnit> groepen = chiroUnitRepository.findAllGroepenWhereGewestStamNummerIs(untrimmedGewestStamNummer);
        // Set the participants and volunteers count
        int count = 1;
        for (ChiroUnit groep : groepen) {
//            System.err.println("INIT GROEP " + count + ") " + groep.getStamNummer() + " " + groep.getName());
            // if internationaal => enkel buddy = true tellen anders enkel buddy = false tellen
            boolean internationaal = checkForInternational(groep.getStamNummer());

            if(internationaal) {
                groep.setParticipantsCountPaid(chiroUnitRepository.countParticipantsInternationaal(Status.PAID));
                groep.setParticipantsCountConfirmed(chiroUnitRepository.countParticipantsInternationaal(Status.CONFIRMED));
                groep.setParticipantsCountUnpaid(chiroUnitRepository.countParticipantsInternationaal(Status.TO_BE_PAID));
                groep.setParticipantsCountCancelled(chiroUnitRepository.countParticipantsInternationaal(Status.CANCELLED));
            } else {
                groep.setParticipantsCountPaid(getCountsParticipantsOnlyGroepen(groep.getStamNummer(), Status.PAID, internationaal));
                groep.setParticipantsCountConfirmed(getCountsParticipantsOnlyGroepen(groep.getStamNummer(), Status.CONFIRMED, internationaal));
                groep.setParticipantsCountUnpaid(getCountsParticipantsOnlyGroepen(groep.getStamNummer(), Status.TO_BE_PAID, internationaal));
                groep.setParticipantsCountCancelled(getCountsParticipantsOnlyGroepen(groep.getStamNummer(), Status.CANCELLED, internationaal));
            }
            // Set the volunteers count based on CAMPGROUND
//            groep.setVolunteersCountConfirmed(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(gewest.getName()), Status.CONFIRMED));
//            groep.setVolunteersCountPaid(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(gewest.getName()), Status.PAID));
//            groep.setVolunteersCountUnpaid(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(gewest.getName()), Status.TO_BE_PAID));
//            groep.setVolunteersCountCancelled(chiroUnitRepository.getCountsVolunteersByCampgroundAndStatus(CampGround.getCamgroundByName(gewest.getName()), Status.CANCELLED));


//            int countAllParticipants = chiroUnitRepository.countParticipantsByGroep(groep.getStamNummer());
//            int countVolunteers = chiroUnitRepository.countVolunteersByGroep(groep.getStamNummer());
////            System.err.println("stamnr: " + groep.getStamNummer() + " #participants+volunteers: "
////                    + countAllParticipants + ", #volunteers: " + countVolunteers);
//            groep.setParticipantsCount(countAllParticipants - countVolunteers);
//            groep.setVolunteersCount(countVolunteers);
//
//            int countAllParticipantsPaid = chiroUnitRepository.countParticipantsByGroep(groep.getStamNummer(), Status.PAID);
//            int countVolunteersPaid = chiroUnitRepository.countVolunteersByGroep(groep.getStamNummer(), Status.PAID);
//            groep.setParticipantsCountPaid(countAllParticipantsPaid - countVolunteersPaid);
//            groep.setVolunteersCountPaid(countVolunteersPaid);


            // Trim the stam nummers.
            groep.setStamNummer(stamNumberTrimmer.trim(groep.getStamNummer()));
            // Set "upper" to gewestStamNummer => used for security filtering (name may remain empty, it is not used for filtering)
            groep.setUpper(new ChiroUnit(gewestStamNummer, ""));
//            System.err.println("AFTERWARDS GROEP " + count++ + ") " + groep.getStamNummer() + " " + groep);
        }
//        System.err.println("GROEPEN LIST from gewest: " + gewestStamNummer + " -- " +groepen);
//        System.err.println("INSIDE getGroepen");
//        for (ChiroUnit chiroUnit : groepen) {
//            System.err.println(chiroUnit);
//        }
        return groepen;
    }

    private int getCountsParticipantsOnlyGroepen(String stamNummer, Status status, boolean countBuddies){

        int countAllParticipantsStatus = chiroUnitRepository.countParticipantsByGroep(stamNummer, status, countBuddies);
        int countVolunteersStatus = chiroUnitRepository.countVolunteersByGroep(stamNummer, status, countBuddies);
//        if(stamNummer.equals("AG /0103")){
//            System.err.println("countAllParticipantsStatus: " + status + " => " + countAllParticipantsStatus);
//            System.err.println("countVolunteersStatus: " + status + " => " + countVolunteersStatus);
//        }
        return countAllParticipantsStatus - countVolunteersStatus;
    }


    // Security
//    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToGetParticipants()")
//    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeParticipants(filterObject)")
    public List<RegistrationParticipant> getRegistrationParticipants(String groepStamNummer){
//        System.err.println("Hi from getRegistrationParticipants");
        // first untrim the groepStamNummer

        //check if groepStamNummer is INT => select only buddies otherwise, select none buddies
        boolean internationaal = checkForInternational(groepStamNummer);

        groepStamNummer = stamNumberTrimmer.untrim(groepStamNummer);
        List<RegistrationParticipant> registrationParticipants;
        List<RegistrationParticipant> registrationParticipantsWithoutVolunteers;
        if(internationaal){
            registrationParticipants = chiroUnitRepository.returnParticipantsInternationaal();
        } else {
            registrationParticipants = chiroUnitRepository.returnParticipantsByGroep(groepStamNummer, internationaal);
        }

        registrationParticipantsWithoutVolunteers = new ArrayList<RegistrationParticipant>();
          

        for (RegistrationParticipant registrationParticipant : registrationParticipants) {
            if(! (registrationParticipant instanceof RegistrationVolunteer) ){
                registrationParticipantsWithoutVolunteers.add(registrationParticipant);
            }
        }

//        System.err.println("Nr of participants in getRegistrationParticipants verbondenservice: " +registrationParticipantsWithoutVolunteers.size());
        return registrationParticipantsWithoutVolunteers;
    }
    // Security
//    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToGetVolunteers()")
//    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeVolunteers(filterObject)")
    public List<RegistrationVolunteer> getRegistrationVolunteers(String groepStamNummer){
//        System.err.println("Hi from getRegistrationVolunteers");

        // first untrim the groepStamNummer
        groepStamNummer = stamNumberTrimmer.untrim(groepStamNummer);
        return chiroUnitRepository.returnVolunteersByGroep(groepStamNummer);
    }

    public List<RegistrationVolunteer> getRegistrationVolunteersByCampground(String campground){
//        System.err.println("chiroUnitRepository.returnVolunteersByCampGround(CampGround.getCamgroundByName(campground));");
        CampGround c = CampGround.getCamgroundByName(campground);

//        System.err.println("Camground found:");
//        System.err.println(c);
//        if(c == null) {
//            System.err.println("CAMPGROUND WAS NULL");
//            return null;
//        } else {
//            System.err.println("Finding volunteers inside "+c.getDescription());
//        }
        List<RegistrationVolunteer> registrationVolunteers = chiroUnitRepository.returnVolunteersByCampGround(c);
        return registrationVolunteers;
    }

}
