package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Queries the DB for all registered participants, calls the excelservice to write
     * them to an xlsx file and returns the file for the requested xlsx file if successful or
     * returns null if something went wrong.
     * @return File for the requested xlsx file or null if something went wrong.
     */
    public File writeRegistrationParticipantsToExcel(Boolean xlsx){
        //TODO: rewrite
//        List<RegistrationParticipant> all = registrationParticipantRepository.findAll();
//        try {
//            File file = excelService.writeExcel(all, xlsx);
//            return file;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * Queries the DB for registered participants only, calls the excelservice to write
     * them to an xlsx file and returns the file for the requested xlsx file if successful or
     * returns null if something went wrong.
     * @return File for the requested xlsx file or null if something went wrong.
     */
    public File writeRegistrationParticipantsToXlsx() {
        //TODO: rewrite
//        try {
//            File file = excelService.writeExcel(getRegistrationParticipantsWithoutVolunteers(), true);
//            return file;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        }
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
         //TODO: rewrite
//         List<RegistrationVolunteer> allVolunteers = registrationVolunteerRepository.findAll();
//         List<RegistrationParticipant> allVolunteerParticipants = new ArrayList<RegistrationParticipant>();
//         for (RegistrationVolunteer registrationVolunteer: allVolunteers) {
//             allVolunteerParticipants.add(registrationVolunteer);
//         }
//
//         try {
//             File file = excelService.writeExcel(allVolunteerParticipants, true);
//             return file;
//         } catch (IOException e) {
//             e.printStackTrace();
//         } catch (InvalidFormatException e) {
//             e.printStackTrace();
//         }
         return null;
     }

    public void createExcelOutputXlsRegistrationAll(HttpServletResponse response) {
        Object[] header = createHeaderForRegistrationParticipants();
        Map<String, Object []> data = createDataForRegistrationParticipants();
        excelOutputService.createExcelOutputXls(response, "registratiesLijstAlles.xls", header, data);
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

    /**
     * Collects the data needed to create the excel file for all RegistrationParticipants.
     * @return
     */
    private Map<String,Object[]> createDataForRegistrationParticipants() {
        List<RegistrationParticipant> all = registrationParticipantRepository.findAll();
        Map<String, Object[]> dataMap = putRegistrationParticipantsIntoMap(all);
        return dataMap;
    }

    /**
     * Collects the data needed to create the excel file for all RegistrationParticipants who are not volunteers.
     * @return
     */
    private Map<String,Object[]> createDataForRegistrationParticipantsOnlyParticipants() {
        List<RegistrationParticipant> participantsWithoutVolunteers = getRegistrationParticipantsWithoutVolunteers();
        Map<String, Object[]> dataMap = putRegistrationParticipantsIntoMap(participantsWithoutVolunteers);
        return dataMap;
    }

    /**
     * Collects the data needed to create the excel file for all RegistrationParticipants who are volunteers.
     * @return
     */
    private Map<String,Object[]> createDataForRegistrationParticipantsOnlyVolunteers() {
        List<RegistrationVolunteer> allVolunteers = registrationVolunteerRepository.findAll();
        List<RegistrationParticipant> allVolunteerParticipants = allVolunteers.stream().collect(Collectors.toList());
        Map<String, Object[]> dataMap = putRegistrationParticipantsIntoMap(allVolunteerParticipants);
        return dataMap;
    }

    /**
     * Creates the header needed to create the excel file for the RegistrationParticipants.
     * @return
     */
    private Object [] createHeaderForRegistrationParticipants(){
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
                "Syncstatus",
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
                precampDates = transformPreCamptListToString(volunteer.getPreCampList());
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
                    syncStatus
            });
        }
        return data;
    }

    private String transformPreCamptListToString(List<PreCamp> preCampList) {
        String dates = "";
        for(int i = 0; i < preCampList.size()-1; i++){
            dates += getDateFormatted(preCampList.get(i).getDate()) + ", ";
        }
        if(preCampList.size() > 0){
            dates += getDateFormatted(preCampList.get(preCampList.size()-1).getDate());
        }
        return dates;
    }

    private String transformPostCampListToString(List<PostCamp> postCampList) {
        String dates = "";
        for(int i = 0; i < postCampList.size()-1; i++){
            dates += getDateFormatted(postCampList.get(i).getDate()) + ", ";
        }
        if(postCampList.size() > 0){
            dates += getDateFormatted(postCampList.get(postCampList.size()-1).getDate());
        }
        return dates;
    }

    private String getDateFormatted(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }


    private String transformLanguageListToString(List<Language> languagesList) {
        String languages = "";
        for(int i = 0; i < languagesList.size()-1; i++){
            languages += languagesList.get(i).getDescription() + ", ";
        }
        if(languagesList.size() > 0){
            languages += languagesList.get(languagesList.size()-1).getDescription();
        }
        return languages;
    }

}
