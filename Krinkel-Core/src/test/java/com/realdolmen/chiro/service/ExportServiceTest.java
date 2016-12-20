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
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void getDateInFormat() {
        Calendar c = Calendar.getInstance();
        c.set(2016,11,25); //months work with index!!
        Date date = c.getTime();
        String dateFormatted = exportService.getDateFormatted(date);
        assertEquals("25-12-2016", dateFormatted);
    }

    @Test
    public void getTimestampInFormat() {
        Calendar c = Calendar.getInstance();
        c.set(2016,11,25,12,00,00); //months work with index!!
        Date date = c.getTime();
        String dateFormatted = exportService.getTimestampFormatted(date);
        assertEquals("25-12-2016 12:00:00", dateFormatted);
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

    @Test
    public void canCreateHeaderForParticipants() {
        Object [] headerObject = exportService.createHeaderForRegistrationParticipants();
        Object object = headerObject[0];
        Object object2 = headerObject[29];
        assertEquals("Id", object);
        assertEquals("Synchstatus", object2);
        assertEquals(31, headerObject.length);
    }

    @Test
    public void canCreateDataForVolunteers() {
        RegistrationVolunteer registrationVolunteer = registrationVolunteerRepository.findAll().get(0);
        List<RegistrationVolunteer> list = new ArrayList<RegistrationVolunteer>();
        list.add(registrationVolunteer);
        Map<String, Object[]> stringMap = exportService.createDataForRegistrationParticipantsOnlyVolunteers();
        Object[] objects = stringMap.get("0");
        assertEquals(60L, objects[0]);
        assertEquals("Gesynchroniseerd", objects[29]);
        assertEquals("13-12-2016 00:00:00", objects[30]);
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


    /**
     * Testing making of XLS
     *
     */

    @Test
    public void compareExcelOutputToTestDataForParticipantsAndVolunteers() throws IOException {
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

    @Test
    public void compareExcelOutputToTestDataForParticipants() throws IOException {
        HttpServletResponse response = new MockHttpServletResponse();
        WritableWorkbook workbook = exportService.createExcelOutputXlsRegistrationParticipants(response);
        assertEquals("Id", readLineFromXls(workbook, 0, 0));
        assertEquals("Ad-nummer", readLineFromXls(workbook, 1, 0));
        assertEquals("50", readLineFromXls(workbook, 0, 5));
        assertEquals("", readLineFromXls(workbook, 14, 1));
        assertEquals("Spaans, Frans", readLineFromXls(workbook, 17, 5));
        assertEquals("21-08-1995", readLineFromXls(workbook, 6, 1));

        assertEquals("THIS CELL WAS EMPTY", readLineFromXls(workbook, 0, 6));
        assertEquals("THIS CELL WAS EMPTY", readLineFromXls(workbook, 29, 6));
        assertEquals("THIS CELL WAS EMPTY", readLineFromXls(workbook, 100, 100));
    }

    @Test
    public void compareExcelOutputToTestDataForVolunteers() throws IOException {
        HttpServletResponse response = new MockHttpServletResponse();
        WritableWorkbook workbook = exportService.createExcelOutputXlsRegistrationVolunteers(response);
        assertEquals("Id", readLineFromXls(workbook, 0, 0));
        assertEquals("Ad-nummer", readLineFromXls(workbook, 1, 0));
        assertEquals("60", readLineFromXls(workbook, 0, 1));
        assertEquals("21-08-1995", readLineFromXls(workbook, 6, 1));
        assertEquals("Vrijwilliger", readLineFromXls(workbook, 13, 1));
        assertEquals("Gesynchroniseerd", readLineFromXls(workbook, 29, 1));
        assertEquals("13-12-2016 00:00:00", readLineFromXls(workbook, 30, 1));

        assertEquals("THIS CELL WAS EMPTY", readLineFromXls(workbook, 100, 3));
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

    /**
     * Testing CSV and ZIP
     */
    @Test
    public void testNrOfFilesInBackupZipAndNames() throws IOException {
        exportService.createCSVBackups();
        ZipFile zipFile = new ZipFile("backup.zip");

        // Check size
        assertEquals(6, zipFile.size());

        String [] temp = {"backupRegistrationParticipants.csv",
                "backupRegistrationVolunteers.csv",
                "backupLoginLogs.csv",
                "backupConfirmationLinks.csv",
                "backupRegistrationCommunications.csv",
                "backupAdmins.csv"
        };
        List<String> fileNames = Arrays.asList(temp);

        // Go over every file and check if the just defined List fileNames contains the fileName
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            assertTrue(fileNames.contains(entry.getName()));
            InputStream stream = zipFile.getInputStream(entry);
        }
        zipFile.close();
    }

    @Test
    public void testBackupZipbackupRegistrationParticipants() throws IOException {
        exportService.createCSVBackups();
        ZipFile zipFile = new ZipFile("backup.zip");
        String fileName = "backupRegistrationParticipants.csv";

        // Go over every file and get the right file
        InputStream inputStream = searchFile(fileName, zipFile);
        File temp = new File("temp1.csv");
        Path destination = Paths.get(temp.getName());
        Files.copy(inputStream, destination);
        inputStream.close(); // Close inputstream in order to be able to delete the temp file
        List<String> rows = getContentOfCSVFile(temp);
        assertEquals("id,adNumber,registeredBy,firstName,lastName,email,emailSubscriber,address,birthdate,lastChange,stamnumber,gender,eventRole,buddy,language,eatinghabbit,remarksFood,socialPromotion,medicalRemarks,remarks,status,syncStatus,httpStatus,phoneNumber",
                rows.get(0));
        assertEquals("50,987654,987654,Frederik,Flodder,email@test.be,email@test.be,Veldstraat 123 1000 Brussel,1995-08-21,null,AG /0202,MAN,ASPI,true,[SPANISH ---  FRENCH],FISHANDMEAT,null,false,null,null,CONFIRMED,null,null,1",
                rows.get(5));
        zipFile.close();
        // Remove temp file
        temp.delete();
    }

    @Test
    public void testBackupZipbackupRegistrationVolunteers() throws IOException {
        exportService.createCSVBackups();
        ZipFile zipFile = new ZipFile("backup.zip");
        String fileName = "backupRegistrationVolunteers.csv";

        // Go over every file and get the right file
        InputStream inputStream = searchFile(fileName, zipFile);
        File temp = new File("temp2.csv");
        Path destination = Paths.get(temp.getName());
        Files.copy(inputStream, destination);
        inputStream.close(); // Close inputstream in order to be able to delete the temp file
        List<String> rows = getContentOfCSVFile(temp);
        assertEquals("campGround,function,preCampList,postCampList,id,adNumber,registeredBy,firstName,lastName,email,emailSubscriber,address,birthdate,lastChange,stamnumber,gender,eventRole,buddy,language,eatinghabbit,remarksFood,socialPromotion,medicalRemarks,remarks,status,syncStatus,httpStatus,phoneNumber",
                rows.get(0));
        assertEquals("KEMPEN,[preset=Kampgrondtrekker --- other=null],[21/08/2017 ---  22/08/2017 ---  23/08/2017],[31/08/2017 ---  01/09/2017 ---  02/09/2017],60,876543,876543,Jos,Flodder,email@test.be,email@test.be,Veldstraat 123 1000 Brussel,1995-08-21,2016-12-13 00:00:00.0,AG /0103,MAN,VOLUNTEER,false,[],FISHANDMEAT,null,false,null,null,CONFIRMED,SYNCED,null,1",
                rows.get(1));
        zipFile.close();
        // Remove temp file
        temp.delete();
    }

    @Test
    public void testBackupZipbackupLoginLogs() throws IOException {
        exportService.createCSVBackups();
        ZipFile zipFile = new ZipFile("backup.zip");
        String fileName = "backupLoginLogs.csv";

        // Go over every file and get the right file
        InputStream inputStream = searchFile(fileName, zipFile);
        File temp = new File("temp3.csv");
        Path destination = Paths.get(temp.getName());
        Files.copy(inputStream, destination);
        inputStream.close(); // Close inputstream in order to be able to delete the temp file
        List<String> rows = getContentOfCSVFile(temp);
        // Data comes from test-data.sql: 12 entries + header
        assertTrue(rows.size() == 13);
        assertEquals("id,adNumber,stamp,stamNumber",
                rows.get(0));
        assertEquals("1,123456,2016-10-01,null",
                rows.get(1));
        zipFile.close();
        // Remove temp file
        temp.delete();
    }

    @Test
    public void testBackupZipbackupRegistrationCommunications() throws IOException {
        exportService.createCSVBackups();
        ZipFile zipFile = new ZipFile("backup.zip");
        String fileName = "backupRegistrationCommunications.csv";

        // Go over every file and get the right file
        InputStream inputStream = searchFile(fileName, zipFile);
        File temp = new File("temp5.csv");
        Path destination = Paths.get(temp.getName());
        Files.copy(inputStream, destination);
        inputStream.close(); // Close inputstream in order to be able to delete the temp file
        List<String> rows = getContentOfCSVFile(temp);
        assertTrue(rows.size() == 9);
        assertEquals("id,adNumber,status,communicationAttempt",
                rows.get(0));
        assertEquals("1,12345,WAITING,0",
                rows.get(1));
        zipFile.close();
        // Remove temp file
        temp.delete();
    }

    @Test
    public void testBackupZipbackupConfirmationLinks() throws IOException {
        exportService.createCSVBackups();
        ZipFile zipFile = new ZipFile("backup.zip");
        String fileName = "backupConfirmationLinks.csv";

        // Go over every file and get the right file
        InputStream inputStream = searchFile(fileName, zipFile);
        File temp = new File("temp5.csv");
        Path destination = Paths.get(temp.getName());
        Files.copy(inputStream, destination);
        inputStream.close(); // Close inputstream in order to be able to delete the temp file
        List<String> rows = getContentOfCSVFile(temp);
        assertTrue(rows.size() == 1);
        assertEquals("No data in DB",
                rows.get(0));
        zipFile.close();
        // Remove temp file
        temp.delete();
    }

    @Test
    public void testBackupZipbackupAdmins() throws IOException {
        exportService.createCSVBackups();
        ZipFile zipFile = new ZipFile("backup.zip");
        String fileName = "backupAdmins.csv";

        // Go over every file and get the right file
        InputStream inputStream = searchFile(fileName, zipFile);
        File temp = new File("temp6.csv");
        Path destination = Paths.get(temp.getName());
        Files.copy(inputStream, destination);
        inputStream.close(); // Close inputstream in order to be able to delete the temp file
        List<String> rows = getContentOfCSVFile(temp);
        assertEquals("adNummer,email,firstname,lastname",
                rows.get(0));
        assertEquals("1,firstAdmin@krinkel.be,first,admin",
                rows.get(1));
        assertEquals("2,secondAdmin@krinkel.be,second,admin",
                rows.get(2));
        zipFile.close();
        // Remove temp file
        temp.delete();
    }

    private InputStream searchFile(String fileName, ZipFile zipFile) throws IOException {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            if (entry.getName().equals(fileName)) {
                return zipFile.getInputStream(entry);
            }
        }
        return null;
    }

    private List<String> getContentOfCSVFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }
}
