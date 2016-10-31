package com.realdolmen.chiro.service;

import com.realdolmen.chiro.chiro_api.ChiroUserAdapter;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.VolunteerFunction;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * TODO: Move to separate module?
 *
 * Mock for an as of yet unavailable Chiro REST API.
 *
 * Maps organisational information about Chiro Groups to a format suitable for
 * the REST endpoint.
 */
@Service
public class ChiroUnitService {

	@Autowired
	private ChiroUnitRepository repository;

	@Autowired
	private ChiroUserAdapter adapter;

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
		verbondUnits = repository.findAllVerbonden();
		for (ChiroUnit verbondUnit : verbondUnits) {
			verbondUnit.setStam(trimStam(verbondUnit.getStam()));
		}

		/* Fix StamNumbers */
		gewestUnits = repository.findAllGewesten();
		for (ChiroUnit gewestUnit : gewestUnits) {
			gewestUnit.setStam(trimStam(gewestUnit.getStam()));
		}

		List<ChiroUnit> chiroUnits = new ArrayList<>();
		// Convert all low level groups
		for (RawChiroUnit rawUnit : repository.findAll()) {
			ChiroUnit unit = new ChiroUnit(trimStam(rawUnit.getStamNumber()), rawUnit.getName());

			ChiroUnit gewest = findByStam(gewestUnits, trimStam(rawUnit.getGewest()));
			gewest.getLower().add(unit);
			gewest.setUpper(findByStam(verbondUnits, trimStam(rawUnit.getVerbond())));
			unit.setUpper(gewest);
			chiroUnits.add(unit);
		}

		for (ChiroUnit gewestUnit : gewestUnits) {
			ChiroUnit verbond = findByStam(verbondUnits, trimStam(gewestUnit.getUpper().getStam()));
			verbond.getLower().add(gewestUnit);
		}

		List<ChiroUnit> combinedUnits = new ArrayList<ChiroUnit>();
		combinedUnits.addAll(verbondUnits);
		combinedUnits.addAll(gewestUnits);
		combinedUnits.addAll(chiroUnits);

		this.chiroUnits = combinedUnits;

		return combinedUnits;
	}

	public List<ChiroUnit> findVerbondUnits() {
		return this.verbondUnits;
	}

	public List<ChiroUnit> findGewestUnits() {
		return this.gewestUnits;
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
			if (unit.getStam().equals(stam)) {
				return unit;
			}
		}
		return null;
	}

	private String trimStam(String stam) {
		return stam.replace("/", "").replace("\\s", "").replace(" ", "");
	}

	@PreAuthorize("@ChiroUnitServiceSecurity.hasPermissionToGetColleagues(#stamnr)")
	@PostFilter("@ChiroUnitServiceSecurity.hasPersmissionToSeeColleaggues(filterObject.)")
	public List<User> getUnitUsers(String stamnr) {
		return adapter.getColleagues(stamnr);
	}

	public List<RegistrationParticipant> findParticipantsByChiroUnit(String stamnummer) {
		ChiroUnit unit = this.find(stamnummer);
		return loopOverRegisteredParticipantsFor(stamnummer, unit);
	}

	private List<RegistrationParticipant> loopOverRegisteredParticipantsFor(String stamnummer, ChiroUnit unit) {
//		ChiroUnit unit = this.find(stamnummer);
		List<RegistrationParticipant> participants = new ArrayList<>();
		if (hasNoLowerLevelUnits(unit)) {
			participants.addAll(registrationParticipantService.findParticipantsByGroup(stamnummer));
		} else {
			if (hasNoHigherLevelUnit(unit)) {
				unit.getLower().forEach(group -> {
					participants.addAll(registrationParticipantService.findParticipantsByGroup(group.getStam()));
				});
			} else {
				unit.getLower().forEach(gewest -> {
					gewest.getLower().forEach(group -> {
						participants.addAll(registrationParticipantService.findParticipantsByGroup(group.getStam()));
					});
				});
			}
		}
		System.err.println(participants.size());
		unit.setParticipantsCount(participants.size());
		return participants;
	}

	public List<RegistrationVolunteer> findVolunteersByChiroUnit(String stamnummer) {
		ChiroUnit unit = this.find(stamnummer);
		return loopOverRegisteredVolunteersFor(stamnummer, unit);
	}

	private List<RegistrationVolunteer> loopOverRegisteredVolunteersFor(String stamnummer, ChiroUnit unit) {
//		ChiroUnit unit = this.find(stamnummer);
		List<RegistrationVolunteer> volunteers = new ArrayList<>();
		if (hasNoLowerLevelUnits(unit)) {
			volunteers.addAll(registrationParticipantService.findVolunteersByGroup(stamnummer));
		} else {
			if (hasNoHigherLevelUnit(unit)) {
				unit.getLower().forEach(gewest -> {
					gewest.getLower().forEach(group -> {
						volunteers.addAll(registrationParticipantService.findVolunteersByGroup(group.getStam()));
					});
				});
			} else {
				unit.getLower().forEach(group -> {
					volunteers.addAll(registrationParticipantService.findVolunteersByGroup(group.getStam()));
				});
			}
		}
		unit.setVolunteersCount(volunteers.size());
		return volunteers;
	}

	private boolean hasNoLowerLevelUnits(ChiroUnit unit) {
		// return unit.getLower() == null;
		return unit.getLower().isEmpty();
	}

	private boolean hasNoHigherLevelUnit(ChiroUnit unit) {
		return unit.getUpper() != null;
	}
}
