package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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

    /**
     * Queries the DB for all registered participants, calls the excelservice to write
     * them to an xlsx file and returns the file for the requested xlsx file if successful or
     * returns null if something went wrong.
     * @return File for the requested xlsx file or null if something went wrong.
     */
    public File writeRegistrationParticipantsToXlsx(){
        List<RegistrationParticipant> all = registrationParticipantRepository.findAll();
        try {
            File file = excelService.writeExcel(all);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}
