package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.util.StamNumberTrimmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
@Service
public class ChiroUnitService {

    @Autowired
    private StamNumberTrimmer stamNumberTrimmer;

    @Autowired
    private ChiroUnitRepository chiroUnitRepository;

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    private List<ChiroUnit> chiroUnits;
    private List<ChiroUnit> verbondUnits;
    private List<ChiroUnit> gewestUnits;

    @PostConstruct
    public void setUp() {
        this.findAll();
    }

    public List<ChiroUnit> findAll() {
        if (this.chiroUnits != null) {
            return this.chiroUnits;
        }

		/* Fix StamNumbers */
        verbondUnits = chiroUnitRepository.findAllVerbonden();
        for (ChiroUnit verbondUnit : verbondUnits) {
            String normalizedStam = stamNumberTrimmer.trim(verbondUnit.getStamNummer());
            verbondUnit.setStamNummer(normalizedStam);
        }

		/* Fix StamNumbers */
        gewestUnits = chiroUnitRepository.findAllGewesten();
        for (ChiroUnit gewestUnit : gewestUnits) {
            String normalizedStam = stamNumberTrimmer.trim(gewestUnit.getStamNummer());
            gewestUnit.setStamNummer(normalizedStam);
        }

        List<ChiroUnit> chiroUnits = new ArrayList<>();
        // Convert all low level groups
        for (RawChiroUnit rawUnit : chiroUnitRepository.findAll()) {
            ChiroUnit unit = new ChiroUnit(
                    stamNumberTrimmer.trim(rawUnit.getGroepStamNummer()),
                    rawUnit.getGroepNaam()
            );

            ChiroUnit gewest = findByStam(gewestUnits, stamNumberTrimmer.trim(rawUnit.getGewestStamNummer()));
            gewest.getLower().add(unit);
            gewest.setUpper(findByStam(verbondUnits, stamNumberTrimmer.trim(rawUnit.getVerbondStamNummer())));
            unit.setUpper(gewest);
            chiroUnits.add(unit);
        }

        for (ChiroUnit gewestUnit : gewestUnits) {
            ChiroUnit verbond = findByStam(verbondUnits, stamNumberTrimmer.trim(gewestUnit.getUpper().getStamNummer()));
            verbond.getLower().add(gewestUnit);
        }

        List<ChiroUnit> combinedUnits = new ArrayList<ChiroUnit>();
        combinedUnits.addAll(verbondUnits);
        combinedUnits.addAll(gewestUnits);
        combinedUnits.addAll(chiroUnits);

        this.chiroUnits = combinedUnits;

        return combinedUnits;
    }

    /**
     * first thing you see in the table (the list of verbonden)
     * @return list of alle verbonden
     */
    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToFindVerbonden()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeVerbonden(filterObject)")
    public List<ChiroUnit> findVerbondUnits() {
        return new ArrayList<>(this.verbondUnits);
    }

    public List<ChiroUnit> findGewestUnits() {
        return this.gewestUnits;
    }

    /**
     * used in the table to get all the gewesten en groepen
     * @param stam
     * @return
     */
    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToFindUnits()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeUnits(filterObject)")
    public List<ChiroUnit> findSubUnits(String stam) {
        List<ChiroUnit> units = this.findAll();
        ChiroUnit unit = this.findByStam(units, stam);
        loopOverRegisteredParticipantsFor(stam, unit);
        loopOverRegisteredVolunteersFor(stam, unit);

        return new ArrayList<>(unit.getLower());
    }

    /**
     * used in the table to get all the participants and their data
     * @param stamnummer
     * @return
     */
    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToGetParticipants()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeParticipants(filterObject)")
    public List<RegistrationParticipant> findParticipantsByChiroUnit(String stamnummer) {
        ChiroUnit unit = this.find(stamnummer);
        return loopOverRegisteredParticipantsFor(stamnummer, unit);
    }

    /**
     * used in the table to get all the volunteers and their data
     * @param stamnummer
     * @return
     */
    @PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToGetVolunteers()")
    @PostFilter("@ChiroUnitServiceSecurity.hasPermissionToSeeVolunteers(filterObject)")
    public List<RegistrationVolunteer> findVolunteersByChiroUnit(String stamnummer) {
        ChiroUnit unit = this.find(stamnummer);
        return loopOverRegisteredVolunteersFor(stamnummer, unit);
    }

    public ChiroUnit find(String stam) {
        List<ChiroUnit> units = this.findAll();
        ChiroUnit unit = this.findByStam(units, stam);
        loopOverRegisteredParticipantsFor(stam, unit);
        loopOverRegisteredVolunteersFor(stam, unit);
        return unit;
    }

    private ChiroUnit findByStam(List<ChiroUnit> units, String stam) {
        for (ChiroUnit unit : units) {
            if (unit.getStamNummer().equals(stam)) {
                return unit;
            }
        }
        return null;
    }

    private List<RegistrationParticipant> loopOverRegisteredParticipantsFor(String stamnummer, ChiroUnit unit) {
//		ChiroUnit unit = this.find(stamnummer);
        List<RegistrationParticipant> participants = new ArrayList<>();
        if (hasNoLowerLevelUnits(unit)) {
            participants.addAll(registrationParticipantService.findParticipantsByGroup(stamnummer));
        } else {
            if (hasNoHigherLevelUnit(unit)) {
                unit.getLower().forEach(group -> {
                    participants.addAll(registrationParticipantService.findParticipantsByGroup(group.getStamNummer()));
                });
            } else {
                unit.getLower().forEach(gewest -> {
                    gewest.getLower().forEach(group -> {
                        participants.addAll(registrationParticipantService.findParticipantsByGroup(group.getStamNummer()));
                    });
                });
            }
        }
        unit.setParticipantsCount(participants.size());
        return participants;
    }

    private List<RegistrationVolunteer> loopOverRegisteredVolunteersFor(String stamnummer, ChiroUnit unit) {
        List<RegistrationVolunteer> volunteers = new ArrayList<>();
        if (hasNoLowerLevelUnits(unit)) {
            volunteers.addAll(registrationParticipantService.findVolunteersByGroup(stamnummer));
        } else {
            if (hasNoHigherLevelUnit(unit)) {
                unit.getLower().forEach(gewest -> {
                    gewest.getLower().forEach(group -> {
                        volunteers.addAll(registrationParticipantService.findVolunteersByGroup(group.getStamNummer()));
                    });
                });
            } else {
                unit.getLower().forEach(group -> {
                    volunteers.addAll(registrationParticipantService.findVolunteersByGroup(group.getStamNummer()));
                });
            }
        }
        unit.setVolunteersCount(volunteers.size());
        return volunteers;
    }

    private boolean hasNoLowerLevelUnits(ChiroUnit unit) {
//         return unit.getLower() == null;
        return unit.getLower().isEmpty();
    }

    private boolean hasNoHigherLevelUnit(ChiroUnit unit) {
        return unit.getUpper() != null;
    }
}
