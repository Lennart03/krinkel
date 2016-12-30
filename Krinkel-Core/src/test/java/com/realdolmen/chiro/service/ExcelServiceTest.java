package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by JCPBB69 on 8/12/2016.
 */
@ContextConfiguration(classes={com.realdolmen.chiro.config.TestConfig.class})
public class ExcelServiceTest extends SpringIntegrationTest {

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    private RegistrationVolunteerRepository registrationVolunteerRepository;

    @Autowired
    private ExportService exportService;

    @Test
    public void writeTest() throws IOException, InvalidFormatException {
        List<RegistrationParticipant> all = registrationParticipantRepository.findAll();
        assertEquals(6, all.size());
        Boolean xlsx = false;

        // TODO: re-write test
//        File myFile = excelService.writeExcel(all, xlsx);
//
//        FileInputStream fileInputStream = new FileInputStream(myFile);
//
//        // Finds the workbook instance for XLSX file
//        Workbook workBook;
//        if(xlsx){
//            workBook = new XSSFWorkbook(fileInputStream);
//        }
//        else{
//            workBook = new HSSFWorkbook(fileInputStream);
//        }
//
//        // Return first sheet from the XLSX workbook
//        Sheet mySheet = workBook.getSheetAt(0);
//
//        assertEquals(7, mySheet.getPhysicalNumberOfRows());
    }

    @Test
    public void writeVolunteersTest() {
        List<RegistrationVolunteer> allVolunteers = registrationVolunteerRepository.findAll();
        assertEquals(1, allVolunteers.size());
    }

//    @Test
//    public void writeParticipantsTestQuery() {
//        List<RegistrationParticipant> allParticipants = registrationParticipantRepository.findRegistrationParticipantsNotVolunteer();
//        assertEquals(5, allParticipants.size());
//    }

    @Test
    public void writeParticipantsTest() {
        assertEquals(5, exportService.getRegistrationParticipantsWithoutVolunteers().size());
    }
}

