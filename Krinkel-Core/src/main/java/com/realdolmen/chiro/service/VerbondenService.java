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
        // Get the verbonden: the name + stamNummer
        List<ChiroUnit> verbonden = chiroUnitRepository.findAllVerbonden();

        // Set the participants count based on VERBOND
        for (ChiroUnit verbond : verbonden) {

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
//            System.err.println("Internationaal is true " + stamNummer);
        } else {
            internationaal = false;
//            System.err.println("Internationaal is false " + stamNummer);
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
        for (ChiroUnit gewest : gewesten) {
            // if internationaal => enkel buddy = true tellen anders enkel buddy = false tellen
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

            // Trim the stam nummers.
            gewest.setStamNummer(stamNumberTrimmer.trim(gewest.getStamNummer()));
            // Set "upper" to verbondStamNummer => used for security filtering (name may remain empty, it is not used for filtering)
            gewest.setUpper(new ChiroUnit(verbondStamNummer, ""));
        }

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

            // Trim the stam nummers.
            groep.setStamNummer(stamNumberTrimmer.trim(groep.getStamNummer()));

            groep.setUpper(new ChiroUnit(gewestStamNummer, ""));
        }
        return groepen;
    }

    private int getCountsParticipantsOnlyGroepen(String stamNummer, Status status, boolean countBuddies){
        int countAllParticipantsStatus = chiroUnitRepository.countParticipantsByGroep(stamNummer, status, countBuddies);
        int countVolunteersStatus = chiroUnitRepository.countVolunteersByGroep(stamNummer, status, countBuddies);
        return countAllParticipantsStatus - countVolunteersStatus;
    }


    // Security
//    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToGetParticipants()")
//    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeParticipants(filterObject)")
    public List<RegistrationParticipant> getRegistrationParticipants(String groepStamNummer){
//        System.err.println("Hi from getRegistrationParticipants");
        // first untrim the groepStamNummer
        groepStamNummer = stamNumberTrimmer.untrim(groepStamNummer);

        //check if groepStamNummer is INT => select only buddies otherwise, select none buddies
        boolean internationaal = checkForInternational(groepStamNummer);

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

        // first untrim the groepStamNummer
        groepStamNummer = stamNumberTrimmer.untrim(groepStamNummer);
        return chiroUnitRepository.returnVolunteersByGroep(groepStamNummer);
    }

    public List<RegistrationVolunteer> getRegistrationVolunteersByCampground(String campground){

        CampGround c = CampGround.getCamgroundByName(campground);

        List<RegistrationVolunteer> registrationVolunteers = chiroUnitRepository.returnVolunteersByCampGround(c);
        return registrationVolunteers;
    }

}
