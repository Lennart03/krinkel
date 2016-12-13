package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.*;
import jxl.write.WritableWorkbook;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by JCPBB69 on 8/12/2016.
 */
@Service
public class ExportService {

    @Autowired
    private ExcelOutputService excelOutputService;

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    private RegistrationVolunteerRepository registrationVolunteerRepository;

    @Autowired
    private LoginLoggerRepository loginLoggerRepository;

    @Autowired
    private ConfirmationLinkRepository confirmationLinkRepository;

    @Autowired
    private RegistrationCommunicationRepository registrationCommunicationRepository;

    public WritableWorkbook createExcelOutputXlsRegistrationAll(HttpServletResponse response) {
        Object[] header = createHeaderForRegistrationParticipants();
        Map<String, Object []> data = createDataForRegistrationParticipants();
        return excelOutputService.createExcelOutputXls(response, "registratiesLijstAlles.xls", header, data);
    }

    public void createExcelOutputXlsRegistrationCSV(HttpServletResponse response) {
        Object[] header = createHeaderForRegistrationParticipants();
        Map<String, Object []> data = createDataForRegistrationParticipants();
        excelOutputService.createExcelOutputXls(response, "registratiesLijstAlles.csv", header, data);
    }

    public void createExcelOutputXlsRegistrationParticipants(HttpServletResponse response) {
        Object[] header = createHeaderForRegistrationParticipants();
        Map<String, Object []> data = createDataForRegistrationParticipantsOnlyParticipants();
        excelOutputService.createExcelOutputXls(response, "registratiesLijstDeelnemers.xls", header, data);
    }

    public void createExcelOutputXlsRegistrationVolunteers(HttpServletResponse response) {
        Object[] header = createHeaderForRegistrationParticipants();
        Map<String, Object []> data = createDataForRegistrationParticipantsOnlyVolunteers();
        excelOutputService.createExcelOutputXls(response, "registratiesLijstMedewerkers.xls", header, data);
    }

    public void createCSVBackups(HttpServletResponse response){
        // All registration participants (not volunteers)
        List<RegistrationParticipant> participants = getRegistrationParticipantsWithoutVolunteers();
        String backupRegistrationParticipantsFileName = "backupRegistrationParticipants.csv";
        createExcelOutputCSVBackup(participants.toArray(),backupRegistrationParticipantsFileName);

        // All registration participants who are volunteers
        List<RegistrationVolunteer> volunteers = registrationVolunteerRepository.findAll();
        String backupRegistrationVolunteersFileName = "backupRegistrationVolunteers.csv";
        createExcelOutputCSVBackup(volunteers.toArray(), backupRegistrationVolunteersFileName);

        // All login_logs
        List<LoginLog> loginLogs = loginLoggerRepository.findAll();
        String backupLoginLogsFileName = "backupLoginLogs.csv";
        createExcelOutputCSVBackup(loginLogs.toArray(), backupLoginLogsFileName);

        // All confirmation links
        List<ConfirmationLink> confirmationLinks = confirmationLinkRepository.findAll();
        String backupConfirmationLinksFileName = "backupConfirmationLinks.csv";
        createExcelOutputCSVBackup(confirmationLinks.toArray(), backupConfirmationLinksFileName);

        // All registration communication
        List<RegistrationCommunication> registrationCommunications = registrationCommunicationRepository.findAll();
        String backupRegistrationCommunicationFileName = "backupRegistrationCommunications.csv";
        createExcelOutputCSVBackup(registrationCommunications.toArray(), backupRegistrationCommunicationFileName);

        // Zip them
        String [] filenames =
                {backupRegistrationParticipantsFileName,
                        backupRegistrationVolunteersFileName,
                        backupLoginLogsFileName,
                        backupConfirmationLinksFileName,
                        backupRegistrationCommunicationFileName};
        zip(filenames, "backup.zip");
//        excelOutputService.exportZip(response, "backup.zip");
//        excelOutputService.exportZip2(response, null);
    }

    /**
     * Zips the files from the filenames given in filenames into the zipfile with name zipFilename.
     * @param filenames
     * @param zipFilename
     */
    private void zip(String[] filenames, String zipFilename) {
        try{
            BufferedInputStream origin = null;
            FileOutputStream dest = null;
            dest = new FileOutputStream(zipFilename);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            int bufferSize = 2048;
            byte data[] = new byte[bufferSize];

            for(int i=0; i < filenames.length; i++) {
                FileInputStream fi = null;
                fi = new FileInputStream(filenames[i]);

                origin = new BufferedInputStream(fi, bufferSize);
                ZipEntry entry = new ZipEntry(filenames[i].substring(filenames[i].lastIndexOf("/") + 1));

                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, bufferSize)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found exception while zipping");
            e.printStackTrace();
            return;
        }
        catch (IOException e) {
            System.err.println("IOException while zipping");
            e.printStackTrace();
        }
    }

    private void createExcelOutputCSVBackup(Object [] objects,
                                            String fileName) {
        if(objects != null && objects.length > 0) {
            ArrayList<String> headersForBackupCSV = getHeadersForBackupCSV(objects[0]);
            ArrayList<ArrayList<String>> stringListForBackupCSV = getStringListForBackupCSV(objects);
            //Add the header to the backup CSV
            stringListForBackupCSV.add(0,headersForBackupCSV);

            //Create the CSV file
            createCSV(stringListForBackupCSV, fileName);
            // Call csv creator with the csv filename
            //excelOutputService.exportCSV(response, fileName);
        }
        else{
            // If no data in DB, make a CSV but with one cell filled in with "No data in DB"
            System.err.println("No objects could be found for CSV backup for file: " + fileName);
            ArrayList<ArrayList<String>> stringListForBackupCSV = new ArrayList<ArrayList<String>>();
            ArrayList<String> noData = new ArrayList<String>();
            noData.add("No data in DB");
            stringListForBackupCSV.add(noData);
            createCSV(stringListForBackupCSV, fileName);
            System.err.println("File created with no information");
        }
    }

    public void createCSV(ArrayList<ArrayList<String>> contentsList, String fileName){
        // For checking if right things are being writed
//        System.err.println("");
//        System.err.println("Contentslist:");
//        int i = 0;
//        for (ArrayList<String> strings : contentsList) {
//            System.err.println("element " + i);
//            System.err.println(strings);
//            i++;
//        }


        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("File not found while trying to create CSV");
            e.printStackTrace();
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (ArrayList<String> strings : contentsList) {
            for (String string : strings) {
                sb.append(string);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("\n");
        }
        pw.write(sb.toString());
        pw.close();
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
     * Collects the data needed to create the excel file for all RegistrationParticipants.
     * @return
     */
    protected Map<String,Object[]> createDataForRegistrationParticipants() {
        List<RegistrationParticipant> all = registrationParticipantRepository.findAll();
        Map<String, Object[]> dataMap = putRegistrationParticipantsIntoMap(all);
        return dataMap;
    }

    /**
     * Collects the data needed to create the excel file for all RegistrationParticipants who are not volunteers.
     * @return
     */
    protected Map<String,Object[]> createDataForRegistrationParticipantsOnlyParticipants() {
        List<RegistrationParticipant> participantsWithoutVolunteers = getRegistrationParticipantsWithoutVolunteers();
        Map<String, Object[]> dataMap = putRegistrationParticipantsIntoMap(participantsWithoutVolunteers);
        return dataMap;
    }

    /**
     * Collects the data needed to create the excel file for all RegistrationParticipants who are volunteers.
     * @return
     */
    protected Map<String,Object[]> createDataForRegistrationParticipantsOnlyVolunteers() {
        List<RegistrationVolunteer> allVolunteers = registrationVolunteerRepository.findAll();
        List<RegistrationParticipant> allVolunteerParticipants = allVolunteers.stream().collect(Collectors.toList());
        Map<String, Object[]> dataMap = putRegistrationParticipantsIntoMap(allVolunteerParticipants);
        return dataMap;
    }

    /**
     * Creates the header needed to create the excel file for the RegistrationParticipants.
     * @return
     */
    protected Object [] createHeaderForRegistrationParticipants(){
        Object [] header = new Object[] {
                "Id",
                "Ad-nummer",
                "Stamnummer",
                "Geslacht",
                "Voornaam",
                "Achternaam",
                "Geboortedatum",
                "Straat",
                "Huisnummer",
                "Postcode",
                "Gemeente",
                "Telefoonnummer",
                "E-mailadres",
                "Evenementsfunctie",
                "Data voorwacht",
                "Data nawacht",
                "Buddy",
                "Talen",
                "Eetgewoonte",
                "Bemerkingen eten",
                "Medische info",
                "Bemerkingen",
                "Kampgrond",
                "Gekozen functie",
                "Andere functie",
                "Sociale promotie",
                "Geregistreerd door",
                "E-mailadres inschrijver",
                "Status",
                "Synchstatus",
                "Laatste wijziging"
        };
        return header;
    }

    public Map<String, Object[]> putRegistrationParticipantsIntoMap(List<RegistrationParticipant> participants) {
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        RegistrationParticipant r = null;
        for(int i = 0; i < participants.size(); i++){
            // Get current participant
            r = participants.get(i);

            // In case of volunteers:
            String campGround = "";
            String preset = "";
            String other = "";
            //Set precamp and postcamp data for volunteers
            String precampDates = "";
            String postcampDates = "";
            if(r instanceof RegistrationVolunteer) {
                RegistrationVolunteer volunteer = (RegistrationVolunteer) r;
                campGround = volunteer.getCampGround().getDescription();
                preset = volunteer.getFunction().getPreset().getDescription();
                other = volunteer.getFunction().getOther();
                precampDates = transformPreCampListToString(volunteer.getPreCampList());
                postcampDates = transformPostCampListToString(volunteer.getPostCampList());
            }

            // Set languages if participant is a buddy
            String languages = "";
            if(r.isBuddy()){
                languages = transformLanguageListToString(r.getLanguage());
            }

            // Translate booleans to yes or no
            String buddy;
            if(r.isBuddy()){
                buddy = "Ja";
            }
            else{
                buddy = "Nee";
            }
            String socialPromotion;
            if(r.isSocialPromotion()) {
                socialPromotion = "Ja";
            } else {
                socialPromotion = "Nee";
            }

            // For formatting dates
            String birthDate = getDateFormatted(r.getBirthdate());
            String lastChange = getDateFormatted(r.getLastChange());


            // Checking for null
            String syncStatus = "";
            if(r.getSyncStatus()!=null){
                syncStatus = r.getSyncStatus().getDescription();
            }

            data.put(""+i, new Object[] {
                    r.getId(),
                    r.getAdNumber(),
                    r.getStamnumber(),
                    r.getGender().getDescription(),
                    r.getFirstName(),
                    r.getLastName(),
                    birthDate,
                    r.getAddress().getStreet(),
                    r.getAddress().getHouseNumber(),
                    r.getAddress().getPostalCode(),
                    r.getAddress().getCity(),
                    r.getPhoneNumber(),
                    r.getEmail(),
                    r.getEventRole().getDescription(),
                    precampDates,
                    postcampDates,
                    buddy,
                    languages,
                    r.getEatinghabbit().getDescription(),
                    r.getRemarksFood(),
                    r.getMedicalRemarks(),
                    r.getRemarks(),
                    campGround,
                    preset,
                    other,
                    socialPromotion,
                    r.getRegisteredBy(),
                    r.getEmailSubscriber(),
                    r.getStatus().getDescription(),
                    syncStatus,
                    lastChange
            });
        }
        return data;
    }

    protected String transformPreCampListToString(List<PreCamp> preCampList) {
        String dates = "";
        for(int i = 0; i < preCampList.size()-1; i++){
            dates += getDateFormatted(preCampList.get(i).getDate()) + ", ";
        }
        if(preCampList.size() > 0){
            dates += getDateFormatted(preCampList.get(preCampList.size()-1).getDate());
        }
        return dates;
    }

    protected String transformPostCampListToString(List<PostCamp> postCampList) {
        String dates = "";
        for(int i = 0; i < postCampList.size()-1; i++){
            dates += getDateFormatted(postCampList.get(i).getDate()) + ", ";
        }
        if(postCampList.size() > 0){
            dates += getDateFormatted(postCampList.get(postCampList.size()-1).getDate());
        }
        return dates;
    }

    protected String getDateFormatted(Date date) {
        if(date == null){
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }



    protected String transformLanguageListToString(List<Language> languagesList) {
        String languages = "";
        for(int i = 0; i < languagesList.size()-1; i++){
            languages += languagesList.get(i).getDescription() + ", ";
        }
        if(languagesList.size() > 0){
            languages += languagesList.get(languagesList.size()-1).getDescription();
        }
        return languages;
    }
    
//    public List<String> getVariableNamesOfObject(Object object){
//        Field[] fields = object.getClass().getDeclaredFields();
//        ArrayList<String> fieldNames = new ArrayList<String>();
//        System.err.println("Declared fields: ");
//        for (Field field : fields) {
//            fieldNames.add(field.getName());
//            System.err.println(field.getName());
//        }
//
//        fields = object.getClass().getFields();
//        System.err.println("Fields: ");
//        for (Field field: fields){
//            fieldNames.add(field.getName());
//            System.err.println(field.getName());
//        }
//        return fieldNames;
//    }

    public String getVariableNamesOfObjectWithBuilder(Object object){
        return ToStringBuilder.reflectionToString(object);
    }

    public ArrayList<ArrayList<String>> getStringListForBackupCSV(Object[] objects){

        ArrayList<ArrayList<String>> stringListForBackupCSV = new ArrayList<ArrayList<String>>();

        for (Object object : objects) {
            String variableNamesOfObjectWithBuilder = getVariableNamesOfObjectWithBuilder(object);

            // Get only the variables list:
            // First split on the first occurence of [
            String variablesList = variableNamesOfObjectWithBuilder.split("\\[",2)[1];
            // Remove the ending ]
            variablesList = variablesList.substring(0,variablesList.length()-1);
//            System.err.println(variablesList);

            // Replace all brackets [ and ] with a quote " so it is easier to split
            variablesList = variablesList.replaceAll("\\[","\"");
            variablesList = variablesList.replaceAll("\\]","\"");
//            System.err.println("Replaced all [ ] with \"");
//            System.err.println(variablesList);

            String[] variables = variablesList.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

//            System.err.println("Split on ',' unless it is between '[' and '], in this case unless it is between \" quotes");
            ArrayList<String> variablesNames = new ArrayList<String>();
            ArrayList<String> variablesValues = new ArrayList<String>();
            for (String variable : variables) {
//                System.err.println(variable);
                //Split on = to separate the name and value
                String[] split = variable.split("=",2);
                variablesNames.add(split[0]);

                String value = split[1];
                //Replace all comma's with semicolon because CSV is comma separated
                value = value.replaceAll(","," --- ");

                // In order to have all null values marked with null
                if(value.equals("<null>")){
                    variablesValues.add("null");
                }else if(value.startsWith("\"")){
                    // If value is a list: change the quotes " " back to brackets [ ]
                    String valueWithoutQuotes = value.substring(1, value.length() - 1);
                    variablesValues.add("[" + valueWithoutQuotes + "]");
                }
                else{
                    variablesValues.add(value);
                }
            }
//            System.err.println("-=Variable names=-");
//            System.err.println(variablesNames);
//            System.err.println("-=Variable values=-");
//            System.err.println(variablesValues);
            stringListForBackupCSV.add(variablesValues);
        }

        return stringListForBackupCSV;
    }

    private ArrayList<String> getHeadersForBackupCSV(Object object) {
        String variableNamesOfObjectWithBuilder = getVariableNamesOfObjectWithBuilder(object);

        // Get only the variables list:
        // First split on the first occurence of [
        String variablesList = variableNamesOfObjectWithBuilder.split("\\[",2)[1];
        // Remove the ending ]
        variablesList = variablesList.substring(0,variablesList.length()-1);
//        System.err.println(variablesList);

        // Replace all brackets [ and ] with a quote " so it is easier to split
        variablesList = variablesList.replaceAll("\\[","\"");
        variablesList = variablesList.replaceAll("\\]","\"");
//        System.err.println("Replaced all [ ] with \"");
//        System.err.println(variablesList);

        String[] variables = variablesList.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

//        System.err.println("Split on ',' unless it is between '[' and '], in this case unless it is between \" quotes");
        ArrayList<String> variablesNames = new ArrayList<String>();
        ArrayList<String> variablesValues = new ArrayList<String>();
        for (String variable : variables) {
//            System.err.println(variable);
            //Split on = to separate the name and value
            String[] split = variable.split("=", 2);
            variablesNames.add(split[0]);
        }
        return variablesNames;
    }

}
