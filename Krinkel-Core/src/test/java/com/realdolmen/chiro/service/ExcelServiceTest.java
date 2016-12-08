package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by JCPBB69 on 8/12/2016.
 */
@ContextConfiguration(classes={com.realdolmen.chiro.config.TestConfig.class})
public class ExcelServiceTest extends SpringIntegrationTest {

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    private ExcelService excelService;

    @Test
    public void writeTest() throws IOException, InvalidFormatException {
        List<RegistrationParticipant> all = registrationParticipantRepository.findAll();
        assertEquals(6, all.size());
        File myFile = excelService.writeExcel(all);

        FileInputStream fileInputStream = new FileInputStream(myFile);

        // Finds the workbook instance for XLSX file
        XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);

        // Return first sheet from the XLSX workbook
        Sheet mySheet = workBook.getSheetAt(0);

        assertEquals(6, mySheet.getPhysicalNumberOfRows());
    }
}
