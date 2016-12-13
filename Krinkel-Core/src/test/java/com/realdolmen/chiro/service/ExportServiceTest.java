package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

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

    @Test
    public void getTwoLanguagesInStringFormat() {
        ArrayList<Language> languageList = new ArrayList<Language>();
        languageList.add(Language.SPANISH);
        languageList.add(Language.FRENCH);
        String languages = exportService.transformLanguageListToString(languageList);
        Assert.assertEquals("Spaans, Frans", languages);
    }

    @Test
    public void getOneLanguageInStringFormat() {
        ArrayList<Language> languageList = new ArrayList<Language>();
        languageList.add(Language.SPANISH);
        String languages = exportService.transformLanguageListToString(languageList);
        Assert.assertEquals("Spaans", languages);
    }

    @Test
    public void getDateInBelgianFormat() {
        Calendar c = Calendar.getInstance();
        c.set(2016,11,25); //months work with index!!
        Date date = c.getTime();
        String dateFormatted = exportService.getDateFormatted(date);
        Assert.assertEquals("25-12-2016", dateFormatted);
    }

    @Test
    public void getPostCampListInStringFormat() {
        ArrayList<PostCamp> postCampList = new ArrayList<PostCamp>();
        postCampList.add(new PostCamp());
        postCampList.add(new PostCamp());
        String postCampListToString = exportService.transformPostCampListToString(postCampList);
        String dateFormatted = exportService.getDateFormatted(new Date());
        Assert.assertEquals(dateFormatted + ", " + dateFormatted, postCampListToString);
    }

    @Test
    public void getPreCampListInStringFormat() {
        ArrayList<PreCamp> preCampList = new ArrayList<PreCamp>();
        preCampList.add(new PreCamp());
        preCampList.add(new PreCamp());
        preCampList.add(new PreCamp());
        String preCampListToString = exportService.transformPreCampListToString(preCampList);
        String dateFormatted = exportService.getDateFormatted(new Date());
        Assert.assertEquals(dateFormatted + ", " + dateFormatted + ", " + dateFormatted, preCampListToString);
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
        Assert.assertEquals(10L,objects[0]);
        Assert.assertEquals("Man",objects[3]);
        Assert.assertEquals("Brussel",objects[10]);
        Assert.assertEquals("email@test.be",objects[12]);
        Assert.assertEquals("Aspi",objects[13]);
        Assert.assertEquals("",objects[15]);
        Assert.assertEquals("Vis en vlees",objects[18]);
        Assert.assertEquals("Te betalen",objects[28]);
        Assert.assertEquals("",objects[29]);
        Assert.assertEquals("",objects[30]);
    }

    // TODO change indexes + length accordingly cf. previous test method!!
    @Test
    public void canCreateHeaderForParticipants() {
        Object [] headerObject = exportService.createHeaderForRegistrationParticipants();
        Object object = headerObject[0];
        Object object2 = headerObject[29];
        Assert.assertEquals("Id",object);
        Assert.assertEquals("Synchstatus",object2);
        Assert.assertEquals(31,headerObject.length);
    }

    // TODO change indexes accordingly cf. previous test method!!
    @Test
    public void canCreateDataForVolunteers() {
        RegistrationVolunteer registrationVolunteer = registrationVolunteerRepository.findAll().get(0);
        List<RegistrationVolunteer> list = new ArrayList<RegistrationVolunteer>();
        list.add(registrationVolunteer);
        Map<String, Object[]> stringMap = exportService.createDataForRegistrationParticipantsOnlyVolunteers();
        Object[] objects = stringMap.get("0");
        Assert.assertEquals(60L,objects[0]);
        Assert.assertEquals("Gesynchroniseerd",objects[29]);
        Assert.assertEquals("13-12-2016",objects[30]);
    }

    @Test
    public void canCreateDataForParticipantsOnly() {
        Map<String, Object[]> map = exportService.createDataForRegistrationParticipantsOnlyParticipants();
        Assert.assertEquals(5, map.size());
    }

    @Test
    public void canCreateDataForAllParticipants() {
        Map<String, Object []> map = exportService.createDataForRegistrationParticipants();
        Assert.assertEquals(6, map.size());
    }

    @Test
    public void canGetRegistrationParticipantsWithoutVolunteers() {
        List<RegistrationParticipant> registrationParticipants = exportService.getRegistrationParticipantsWithoutVolunteers();
        Assert.assertEquals(5,registrationParticipants.size());
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
