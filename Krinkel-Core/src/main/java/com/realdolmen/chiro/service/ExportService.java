package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.EventRole;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JCPBB69 on 8/12/2016.
 */
@Service
public class ExportService {
    @Autowired
    private ExcelService excelService;

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    private RegistrationVolunteerRepository registrationVolunteerRepository;

    /**
     * Queries the DB for all registered participants, calls the excelservice to write
     * them to an xlsx file and returns the file for the requested xlsx file if successful or
     * returns null if something went wrong.
     * @return File for the requested xlsx file or null if something went wrong.
     */
    public File writeRegistrationParticipantsToExcel(Boolean xlsx){

        List<RegistrationParticipant> all = registrationParticipantRepository.findAll();
        try {
            File file = excelService.writeExcel(all, xlsx);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries the DB for registered participants only, calls the excelservice to write
     * them to an xlsx file and returns the file for the requested xlsx file if successful or
     * returns null if something went wrong.
     * @return File for the requested xlsx file or null if something went wrong.
     */
    public File writeRegistrationParticipantsToXlsx() {
        try {
            File file = excelService.writeExcel(getRegistrationParticipantsWithoutVolunteers());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RegistrationParticipant> getRegistrationParticipantsWithoutVolunteers(){
        List<RegistrationParticipant> allParticipants = registrationParticipantRepository.findAll();
        List<RegistrationParticipant> allParticipantsNotVolunteer = new ArrayList<RegistrationParticipant>();
        for (RegistrationParticipant registrationParticipant : allParticipants) {
            if(registrationParticipant.getEventRole() != EventRole.VOLUNTEER){
                allParticipantsNotVolunteer.add(registrationParticipant);
            }
        }
        return allParticipantsNotVolunteer;
    }

    /**
     * Queries the DB for registered volunteers only, calls the excelservice to write
     * them to an xlsx file and returns the file for the requested xlsx file if successful or
     * returns null if something went wrong.
     * @return File for the requested xlsx file or null if something went wrong.
     * */
     public File writeRegistrationVolunteersToXlsx() {
         List<RegistrationVolunteer> allVolunteers = registrationVolunteerRepository.findAll();
         List<RegistrationParticipant> allVolunteerParticipants = new ArrayList<RegistrationParticipant>();
         for (RegistrationVolunteer registrationVolunteer: allVolunteers) {
             allVolunteerParticipants.add(registrationVolunteer);
         }

         try {
             File file = excelService.writeExcel(allVolunteerParticipants);
             return file;
         } catch (IOException e) {
             e.printStackTrace();
         } catch (InvalidFormatException e) {
             e.printStackTrace();
         }
         return null;
     }
}
