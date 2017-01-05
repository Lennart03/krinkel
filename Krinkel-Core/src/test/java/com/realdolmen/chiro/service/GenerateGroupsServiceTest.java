package com.realdolmen.chiro.service;

import com.realdolmen.chiro.config.TestConfig;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.xml.bind.SchemaOutputResolver;
import java.util.*;

/**
 * Created by MHSBB71 on 16/12/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GenerateGroupsServiceTest{

    @Mock
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Mock
    private ChiroUnitRepository chiroUnitRepository;

    @Autowired
    @InjectMocks
    private GenerateGroupsService generateGroupsService;

    @Test
    public void separateGroupsInCorrectListsTest() {
        List<RegistrationParticipant> registrationParticipants =  new ArrayList<>();
        RegistrationParticipant.RegistrationParticipantBuilder builder = new RegistrationParticipant.RegistrationParticipantBuilder();
        builder.adNumber("1");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.MAN);
        builder.stamnumber("1");
        registrationParticipants.add(builder.build());

        builder.adNumber("2");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.WOMAN);
        builder.stamnumber("2");
        registrationParticipants.add(builder.build());

        builder.adNumber("3");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.MAN);
        builder.stamnumber("3");
        registrationParticipants.add(builder.build());

        builder.adNumber("4");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.WOMAN);
        builder.stamnumber("4");
        registrationParticipants.add(builder.build());

        builder.adNumber("5");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.MAN);
        builder.stamnumber("5");
        registrationParticipants.add(builder.build());

        builder.adNumber("6");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.WOMAN);
        builder.stamnumber("6");
        registrationParticipants.add(builder.build());

        builder.adNumber("7");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.MAN);
        builder.stamnumber("7");
        registrationParticipants.add(builder.build());

        builder.adNumber("8");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.WOMAN);
        builder.stamnumber("8");
        registrationParticipants.add(builder.build());

        builder.adNumber("9");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.MAN);
        builder.stamnumber("9");
        registrationParticipants.add(builder.build());

        builder.adNumber("10");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.WOMAN);
        builder.stamnumber("10");
        registrationParticipants.add(builder.build());

        builder.adNumber("11");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.MAN);
        builder.stamnumber("11");
        registrationParticipants.add(builder.build());

        builder.adNumber("12");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.WOMAN);
        builder.stamnumber("12");
        registrationParticipants.add(builder.build());

        builder.adNumber("13");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.MAN);
        builder.stamnumber("13");
        registrationParticipants.add(builder.build());

        Mockito.when(chiroUnitRepository.findUnionParticipant("1")).thenReturn(new ChiroUnit("A", "A"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("2")).thenReturn(new ChiroUnit("A", "A"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("3")).thenReturn(new ChiroUnit("B", "B"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("4")).thenReturn(new ChiroUnit("B", "B"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("5")).thenReturn(new ChiroUnit("A", "A"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("6")).thenReturn(new ChiroUnit("A", "A"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("7")).thenReturn(new ChiroUnit("B", "B"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("8")).thenReturn(new ChiroUnit("C", "C"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("9")).thenReturn(new ChiroUnit("A", "A"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("10")).thenReturn(new ChiroUnit("B", "B"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("11")).thenReturn(new ChiroUnit("B", "B"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("12")).thenReturn(new ChiroUnit("A", "A"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("13")).thenReturn(new ChiroUnit("A", "A"));

        Iterator<RegistrationParticipant> registrationIterator = registrationParticipants.iterator();

        while (registrationIterator.hasNext()) {
            RegistrationParticipant participant = registrationIterator.next();
            generateGroupsService.addParticipantInCorrectGroup(participant);
        }

        //A -> 4M 3W
        //B -> 3M 2W
        //C -> 1W
        Assert.assertNotNull(generateGroupsService.getParticipantsMan());
        Assert.assertNotNull(generateGroupsService.getParticipantsWoman());

        Assert.assertEquals(2, generateGroupsService.getParticipantsMan().size());
        Assert.assertEquals(3, generateGroupsService.getParticipantsWoman().size());

        Assert.assertEquals(4, generateGroupsService.getParticipantsMan().get("A").size());
        Assert.assertEquals(3, generateGroupsService.getParticipantsWoman().get("A").size());

        Assert.assertEquals(3, generateGroupsService.getParticipantsMan().get("B").size());
        Assert.assertEquals(2, generateGroupsService.getParticipantsWoman().get("B").size());

        Assert.assertEquals(1, generateGroupsService.getParticipantsWoman().get("C").size());
    }

    @Test
    public void addManToListTest() {
        List<RegistrationParticipant> tempList = new ArrayList<>();
        Map<String, List<RegistrationParticipant>> participantsMan = new HashMap<>();

        Assert.assertFalse(generateGroupsService.addManToList("A", tempList));

        generateGroupsService.setParticipantsMan(participantsMan);

        Assert.assertFalse(generateGroupsService.addManToList("A", tempList));

        List<RegistrationParticipant> participantsUnionA = new ArrayList<>();
        RegistrationParticipant.RegistrationParticipantBuilder builder = new RegistrationParticipant.RegistrationParticipantBuilder();
        builder.gender(Gender.MAN);
        builder.adNumber("1");

        participantsUnionA.add(builder.build());
        builder.adNumber("2");
        participantsUnionA.add(builder.build());

        participantsMan.put("A", participantsUnionA);

        generateGroupsService.setParticipantsMan(participantsMan);

        Assert.assertEquals(2, generateGroupsService.getParticipantsMan().get("A").size());
        Assert.assertTrue(generateGroupsService.addManToList("A", tempList));
        Assert.assertEquals(1, generateGroupsService.getParticipantsMan().get("A").size());
        Assert.assertEquals(1, tempList.size());
        Assert.assertEquals(Gender.MAN, tempList.get(0).getGender());
    }

    @Test
    public void addWomanToListTest() {
        List<RegistrationParticipant> tempList = new ArrayList<>();
        Map<String, List<RegistrationParticipant>> participantsWoman = new HashMap<>();

        Assert.assertFalse(generateGroupsService.addWomanToList("A", tempList));

        generateGroupsService.setParticipantsWoman(participantsWoman);

        Assert.assertFalse(generateGroupsService.addWomanToList("A", tempList));

        List<RegistrationParticipant> participantsUnionA = new ArrayList<>();
        RegistrationParticipant.RegistrationParticipantBuilder builder = new RegistrationParticipant.RegistrationParticipantBuilder();
        builder.gender(Gender.WOMAN);
        builder.adNumber("1");

        participantsUnionA.add(builder.build());
        builder.adNumber("2");
        participantsUnionA.add(builder.build());

        participantsWoman.put("A", participantsUnionA);

        generateGroupsService.setParticipantsWoman(participantsWoman);

        Assert.assertEquals(2, generateGroupsService.getParticipantsWoman().get("A").size());
        Assert.assertTrue(generateGroupsService.addWomanToList("A", tempList));
        Assert.assertEquals(1, generateGroupsService.getParticipantsWoman().get("A").size());
        Assert.assertEquals(1, tempList.size());
        Assert.assertEquals(Gender.WOMAN, tempList.get(0).getGender());
    }


    @Test
    public void addParticipantUnionToListWithoutAmountGenderRestrictionsTest() {
        List<RegistrationParticipant> tempList = new ArrayList<>();
        Map<String, List<RegistrationParticipant>> participantsWoman = new HashMap<>();
        Map<String, List<RegistrationParticipant>> participantsMan = new HashMap<>();

        List<RegistrationParticipant> participantsManUnionA = new ArrayList<>();
        List<RegistrationParticipant> participantsWomanUnionA = new ArrayList<>();
        RegistrationParticipant.RegistrationParticipantBuilder builder = new RegistrationParticipant.RegistrationParticipantBuilder();
        builder.gender(Gender.WOMAN);
        builder.adNumber("1");

        participantsWomanUnionA.add(builder.build());
        builder.adNumber("2");
        participantsWomanUnionA.add(builder.build());

        participantsWoman.put("A", participantsWomanUnionA);

        generateGroupsService.setParticipantsWoman(participantsWoman);

        builder.gender(Gender.MAN);
        builder.adNumber("3");
        participantsManUnionA.add(builder.build());
        builder.adNumber("4");
        participantsManUnionA.add(builder.build());
        participantsMan.put("A", participantsManUnionA);

        generateGroupsService.setParticipantsMan(participantsMan);

        generateGroupsService.setAmountMan(2);
        generateGroupsService.setAmountWoman(2);
        generateGroupsService.addParticipantUnionToList("A", tempList);
        Assert.assertEquals(1, tempList.size());
        if(tempList.get(0).getGender().equals(Gender.MAN)) {
            Assert.assertEquals(3, generateGroupsService.getAmountMan());
        } else if(tempList.get(0).getGender().equals(Gender.WOMAN)) {
            Assert.assertEquals(3, generateGroupsService.getAmountWoman());
        }
    }

    @Test
    public void addParticipantUnionToListWhenGenderRestrictionsTest() {
        List<RegistrationParticipant> tempList = new ArrayList<>();
        Map<String, List<RegistrationParticipant>> participantsWoman = new HashMap<>();
        Map<String, List<RegistrationParticipant>> participantsMan = new HashMap<>();

        List<RegistrationParticipant> participantsManUnionA = new ArrayList<>();
        List<RegistrationParticipant> participantsWomanUnionA = new ArrayList<>();
        RegistrationParticipant.RegistrationParticipantBuilder builder = new RegistrationParticipant.RegistrationParticipantBuilder();
        builder.gender(Gender.WOMAN);
        builder.adNumber("1");

        participantsWomanUnionA.add(builder.build());
        builder.adNumber("2");
        participantsWomanUnionA.add(builder.build());

        participantsWoman.put("A", participantsWomanUnionA);

        generateGroupsService.setParticipantsWoman(participantsWoman);

        builder.gender(Gender.MAN);
        builder.adNumber("3");
        participantsManUnionA.add(builder.build());
        builder.adNumber("4");
        participantsManUnionA.add(builder.build());
        participantsMan.put("A", participantsManUnionA);

        generateGroupsService.setParticipantsMan(participantsMan);

        int amountMan = generateGroupsService.getGroupSize() / 2;

        generateGroupsService.setAmountMan(amountMan);
        generateGroupsService.addParticipantUnionToList("A", tempList);

        Assert.assertEquals(1, tempList.size());
        Assert.assertEquals(Gender.WOMAN, tempList.get(0).getGender());
        Assert.assertEquals(1, generateGroupsService.getAmountWoman());

        int amountWoman = generateGroupsService.getGroupSize() / 2;
        generateGroupsService.setAmountMan(0);
        generateGroupsService.setAmountWoman(amountWoman);

        generateGroupsService.addParticipantUnionToList("A", tempList);

        Assert.assertEquals(2, tempList.size());
        Assert.assertEquals(Gender.MAN, tempList.get(1).getGender());
        Assert.assertEquals(1, generateGroupsService.getAmountMan());

        amountMan = amountWoman;
        generateGroupsService.setAmountMan(amountMan);

        generateGroupsService.addParticipantUnionToList("A", tempList);
        Assert.assertEquals(2, tempList.size());
        Assert.assertEquals(6, generateGroupsService.getAmountMan());
        Assert.assertEquals(6, generateGroupsService.getAmountWoman());
    }

    @Test
    public void assignParticipantsToGroupTest() {
        List<RegistrationParticipant> registrationParticipants =  new ArrayList<>();
        RegistrationParticipant.RegistrationParticipantBuilder builder = new RegistrationParticipant.RegistrationParticipantBuilder();
        builder.adNumber("1");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.MAN);
        registrationParticipants.add(builder.build());

        builder.adNumber("2");
        registrationParticipants.add(builder.build());

        builder.adNumber("3");
        registrationParticipants.add(builder.build());

        builder.adNumber("4");
        registrationParticipants.add(builder.build());

        builder.adNumber("5");
        registrationParticipants.add(builder.build());

        builder.adNumber("6");
        registrationParticipants.add(builder.build());

        builder.adNumber("7");
        registrationParticipants.add(builder.build());

        builder.adNumber("8");
        registrationParticipants.add(builder.build());

        builder.adNumber("9");
        registrationParticipants.add(builder.build());

        builder.adNumber("10");
        registrationParticipants.add(builder.build());

        builder.adNumber("11");
        registrationParticipants.add(builder.build());

        builder.adNumber("12");
        registrationParticipants.add(builder.build());

        builder.adNumber("13");
        builder.birthdate(new Date(2000 - (int) (Math.random() * 50), 01, 01));
        builder.gender(Gender.WOMAN);
        builder.stamnumber("13");
        registrationParticipants.add(builder.build());

        builder.adNumber("14");
        registrationParticipants.add(builder.build());
        builder.adNumber("15");
        registrationParticipants.add(builder.build());
        builder.adNumber("16");
        registrationParticipants.add(builder.build());
        builder.adNumber("17");
        registrationParticipants.add(builder.build());
        builder.adNumber("18");
        registrationParticipants.add(builder.build());
        builder.adNumber("19");
        registrationParticipants.add(builder.build());
        builder.adNumber("20");
        registrationParticipants.add(builder.build());
        builder.adNumber("21");
        registrationParticipants.add(builder.build());
        builder.adNumber("22");
        registrationParticipants.add(builder.build());
        builder.adNumber("23");
        registrationParticipants.add(builder.build());
        builder.adNumber("24");
        registrationParticipants.add(builder.build());

        Mockito.when(chiroUnitRepository.findUnionParticipant("1")).thenReturn(new ChiroUnit("A", "A"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("13")).thenReturn(new ChiroUnit("A", "A"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("2")).thenReturn(new ChiroUnit("B", "B"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("14")).thenReturn(new ChiroUnit("B", "B"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("3")).thenReturn(new ChiroUnit("C", "C"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("15")).thenReturn(new ChiroUnit("C", "C"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("4")).thenReturn(new ChiroUnit("D", "D"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("16")).thenReturn(new ChiroUnit("D", "D"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("5")).thenReturn(new ChiroUnit("E", "E"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("17")).thenReturn(new ChiroUnit("E", "E"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("6")).thenReturn(new ChiroUnit("F", "F"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("18")).thenReturn(new ChiroUnit("F", "F"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("7")).thenReturn(new ChiroUnit("G", "G"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("19")).thenReturn(new ChiroUnit("G", "G"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("8")).thenReturn(new ChiroUnit("H", "H"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("20")).thenReturn(new ChiroUnit("H", "H"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("9")).thenReturn(new ChiroUnit("I", "I"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("21")).thenReturn(new ChiroUnit("I", "I"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("10")).thenReturn(new ChiroUnit("J", "J"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("22")).thenReturn(new ChiroUnit("J", "J"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("11")).thenReturn(new ChiroUnit("K", "K"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("23")).thenReturn(new ChiroUnit("K", "K"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("12")).thenReturn(new ChiroUnit("L", "L"));
        Mockito.when(chiroUnitRepository.findUnionParticipant("24")).thenReturn(new ChiroUnit("L", "L"));


        List<ChiroUnit> unions = new ArrayList<>();
        unions.add(new ChiroUnit("A", "A"));
        unions.add(new ChiroUnit("B", "B"));
        unions.add(new ChiroUnit("C", "C"));
        unions.add(new ChiroUnit("D", "D"));
        unions.add(new ChiroUnit("E", "E"));
        unions.add(new ChiroUnit("F", "F"));
        unions.add(new ChiroUnit("G", "G"));
        unions.add(new ChiroUnit("H", "H"));
        unions.add(new ChiroUnit("I", "I"));
        unions.add(new ChiroUnit("J", "J"));
        unions.add(new ChiroUnit("K", "K"));
        unions.add(new ChiroUnit("L", "L"));
        Mockito.when(chiroUnitRepository.findAllVerbonden()).thenReturn(unions);
        chiroUnitRepository.findAllVerbonden();

        Iterator<RegistrationParticipant> registrationIterator = registrationParticipants.iterator();

        while (registrationIterator.hasNext()) {
            RegistrationParticipant participant = registrationIterator.next();
            generateGroupsService.addParticipantInCorrectGroup(participant);
        }

        List<RegistrationParticipant> tempList = generateGroupsService.assignParticipantsToGroup();

        Assert.assertEquals((int)generateGroupsService.getGroupSize(), tempList.size());

        int amountMan = 0;
        int amountWoman = 0;
        for(int i = 0; i < tempList.size(); i++) {
            if(tempList.get(i).getGender().equals(Gender.MAN)) {
                amountMan++;
            } else if(tempList.get(i).getGender().equals((Gender.WOMAN))) {
                amountWoman++;
            }
        }

        if(generateGroupsService.getGroupSize() % 2 == 0) {
            System.out.println(amountWoman + "  " + amountMan);
            Assert.assertEquals(amountWoman, amountMan);
        } else {
            Assert.assertTrue((amountWoman == amountMan + 1) || (amountWoman + 1 == amountMan));
        }
    }
}
