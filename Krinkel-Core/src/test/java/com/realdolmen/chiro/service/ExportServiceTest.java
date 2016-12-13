package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import jxl.CellType;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by HBSBB70 on 12/12/2016.
 */
@ContextConfiguration (classes={com.realdolmen.chiro.config.TestConfig.class})
public class ExportServiceTest extends SpringIntegrationTest {

    @Autowired
    RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    RegistrationVolunteerRepository registrationVolunteerRepository;

    @Autowired
    ExportService exportService;

//    @Mock
//    private HttpServletResponse response;

    @Test
    public void getTwoLanguagesInStringFormat() {
        ArrayList<Language> languageList = new ArrayList<Language>();
        languageList.add(Language.SPANISH);
        languageList.add(Language.FRENCH);
        String languages = exportService.transformLanguageListToString(languageList);
        assertEquals("Spaans, Frans", languages);
    }

    @Test
    public void getOneLanguageInStringFormat() {
        ArrayList<Language> languageList = new ArrayList<Language>();
        languageList.add(Language.SPANISH);
        String languages = exportService.transformLanguageListToString(languageList);
        assertEquals("Spaans", languages);
    }

    @Test
    public void getDateInBelgianFormat() {
        Calendar c = Calendar.getInstance();
        c.set(2016,11,25); //months work with index!!
        Date date = c.getTime();
        String dateFormatted = exportService.getDateFormatted(date);
        assertEquals("25-12-2016", dateFormatted);
    }

    @Test
    public void getPostCampListInStringFormat() {
        ArrayList<PostCamp> postCampList = new ArrayList<PostCamp>();
        postCampList.add(new PostCamp());
        postCampList.add(new PostCamp());
        String postCampListToString = exportService.transformPostCampListToString(postCampList);
        String dateFormatted = exportService.getDateFormatted(new Date());
        assertEquals(dateFormatted + ", " + dateFormatted, postCampListToString);
    }

    @Test
    public void getPreCampListInStringFormat() {
        ArrayList<PreCamp> preCampList = new ArrayList<PreCamp>();
        preCampList.add(new PreCamp());
        preCampList.add(new PreCamp());
        preCampList.add(new PreCamp());
        String preCampListToString = exportService.transformPreCampListToString(preCampList);
        String dateFormatted = exportService.getDateFormatted(new Date());
        assertEquals(dateFormatted + ", " + dateFormatted + ", " + dateFormatted, preCampListToString);
    }

    // TODO change indexes of 'objects' according to the method in ExportService!!!
    @Test
    public void getParticipantsInMap() {
        RegistrationParticipant registrationParticipant = registrationParticipantRepository.findAll().get(0);
        List<RegistrationParticipant> list = new ArrayList<RegistrationParticipant>();
        list.add(registrationParticipant);
        Map<String, Object[]> stringMap = exportService.putRegistrationParticipantsIntoMap(list);
        System.err.println("SIZE OF STRINGMAP IS = " + stringMap.size());
        Object[] objects = stringMap.get("0");
        assertEquals(10L, objects[0]);
        assertEquals("Man", objects[3]);
        assertEquals("Brussel", objects[10]);
        assertEquals("email@test.be", objects[12]);
        assertEquals("Aspi", objects[13]);
        assertEquals("", objects[15]);
        assertEquals("Vis en vlees", objects[18]);
        assertEquals("Te betalen", objects[28]);
        assertEquals("", objects[29]);
        assertEquals("", objects[30]);
    }

    // TODO change indexes + length accordingly cf. previous test method!!
    @Test
    public void canCreateHeaderForParticipants() {
        Object [] headerObject = exportService.createHeaderForRegistrationParticipants();
        Object object = headerObject[0];
        Object object2 = headerObject[29];
        assertEquals("Id", object);
        assertEquals("Synchstatus", object2);
        assertEquals(31, headerObject.length);
    }

    // TODO change indexes accordingly cf. previous test method!!
    @Test
    public void canCreateDataForVolunteers() {
        RegistrationVolunteer registrationVolunteer = registrationVolunteerRepository.findAll().get(0);
        List<RegistrationVolunteer> list = new ArrayList<RegistrationVolunteer>();
        list.add(registrationVolunteer);
        Map<String, Object[]> stringMap = exportService.createDataForRegistrationParticipantsOnlyVolunteers();
        Object[] objects = stringMap.get("0");
        assertEquals(60L, objects[0]);
        assertEquals("Gesynchroniseerd", objects[29]);
        assertEquals("13-12-2016", objects[30]);
    }

    @Test
    public void canCreateDataForParticipantsOnly() {
        Map<String, Object[]> map = exportService.createDataForRegistrationParticipantsOnlyParticipants();
        assertEquals(5, map.size());
    }

    @Test
    public void canCreateDataForAllParticipants() {
        Map<String, Object []> map = exportService.createDataForRegistrationParticipants();
        assertEquals(6, map.size());
    }

    @Test
    public void canGetRegistrationParticipantsWithoutVolunteers() {
        List<RegistrationParticipant> registrationParticipants = exportService.getRegistrationParticipantsWithoutVolunteers();
        assertEquals(5, registrationParticipants.size());
    }

    @Test
    public void downloadCompleteRegistrationParticipantsList() throws IOException {
        HttpServletResponse response = new MockHttpServletResponse();
        WritableWorkbook workbook = exportService.createExcelOutputXlsRegistrationAll(response);
        assertEquals("Id", readLineFromXls(workbook, 0, 0));
        assertEquals("Ad-nummer", readLineFromXls(workbook, 1, 0));
        assertEquals("60", readLineFromXls(workbook, 0, 6));
        assertEquals("", readLineFromXls(workbook, 14, 1));
        assertEquals("Spaans, Frans", readLineFromXls(workbook, 17, 5));
        assertEquals("Gesynchroniseerd", readLineFromXls(workbook, 29, 6));
        assertEquals("THIS CELL WAS EMPTY", readLineFromXls(workbook, 100, 100));
    }

    /**
     * Returns the string value of the cell at the given column and row indexes inside the given writableworkbook.
     * This is to check the contents of cells for writableworkbooks of the jexcel class.
     * @param workBook
     * @param column
     * @param row
     * @return
     * @throws IOException
     */
    private String readLineFromXls(WritableWorkbook workBook, int column, int row) throws IOException {

//        File file = new File(fileName);
//        FileInputStream fis = new FileInputStream(file);
        // Finds the workbook instance for XLS file
//        HSSFWorkbook workBook = new HSSFWorkbook(fis);
        // Return first sheet from the XLS workbook
        WritableSheet sheet = workBook.getSheet(0);
        WritableCell cell = sheet.getWritableCell(column, row);
        if(cell.getType() == CellType.EMPTY){
            return "THIS CELL WAS EMPTY";
        }
        if (cell!=null) {
            return cell.getContents();
        }
        return null;
    }
//    @Test
//    public void compareExcelOutputToTestDataForAllParticipants() {
//        exportService.createExcelOutputXlsRegistrationAll(HttpServletResponse response);
//
//    }

//    @Test
//    public void compareExcelOutputToTestDataForParticipants() {
//
//    }
//
//    @Test
//    public void compareExcelOutputToTestDataForVolunteers() {
//
//    }
//
//    @Test
//    public void compareCsvOutputToTestDataForAllParticipants() {
//
//    }
}
