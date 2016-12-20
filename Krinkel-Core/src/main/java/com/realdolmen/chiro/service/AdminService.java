package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.units.Admin;
import com.realdolmen.chiro.domain.units.ChiroContact;
import com.realdolmen.chiro.exception.NoContactFoundException;
import com.realdolmen.chiro.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
     * @param adNumber unique number by chiro to identify a person
     * @throws URISyntaxException
     */
    public void addNewAdmin(Integer adNumber) throws URISyntaxException, NoContactFoundException, IOException {
        ChiroContact chiroContact = chiroContactService.getChiroContact(adNumber);
        Admin admin = new Admin(adNumber, chiroContact.getEmail(), chiroContact.getFirstName(), chiroContact.getLastName());
        adminRepository.save(admin);
    }

    public String getChiroMember(Integer adNumber) throws URISyntaxException {
        return chiroContactService.getContact(adNumber);
    }

    public void deleteAdmin(Integer adNumber) {
        adminRepository.delete(adNumber);
    }
}
