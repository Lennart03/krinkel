package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

/**
 * This service is used by the ChiroAdminController to add new admin member and to retrieve all the admins
 */
@Service
public class AdminService {

    @Autowired
    ChiroContactService chiroContactService;

    @Autowired
    CASService casService;

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Commands the AdminRepository to find all the admins.
     *
     * @return A list of all the admins
     */
    public List<Admin> getAdmins() {
        return adminRepository.findAll();
    }

    /**
     * Adds a new admin based on his adnumber. To do this, first a call is made to the ChiroAPI to retrieve the person.
     * Afterwards it is save using the AdminRepository.
     *
     * @param adnummer unique number by chiro to identify a person
     * @throws URISyntaxException
     */
    public void addNewAdmin(Integer adnummer) throws URISyntaxException {
        RegistrationParticipant registrationParticipant = chiroContactService.getRegistrationParticipant(adnummer);
        Admin admin = new Admin(adnummer, registrationParticipant.getEmail(), registrationParticipant.getFirstName(), registrationParticipant.getLastName());
        adminRepository.save(admin);
        casService.addAdmin(adnummer.toString());
    }

    public String getChiroMember(Integer adnummer) throws URISyntaxException {
        return chiroContactService.getContact(adnummer);
    }

    public void deleteAdmin(Integer adnummer) {
        adminRepository.delete(adnummer);
    }
}
