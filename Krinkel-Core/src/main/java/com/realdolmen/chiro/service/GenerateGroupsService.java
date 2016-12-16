package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by MHSBB71 on 15/12/2016.
 */
@Service
public class GenerateGroupsService {
    @Autowired
    ChiroUnitRepository chiroUnitRepository;

    @Autowired
    RegistrationParticipantRepository registrationParticipantRepository;


    private Logger logger = LoggerFactory.getLogger(GenerateGroupsService.class);

    private static Integer GROUPSIZE = 12;

    private Map<String, List<RegistrationParticipant>> participantsMale = new HashMap<>();
    private Map<String, List<RegistrationParticipant>> participantsFemale = new HashMap<>();
    private Map<String, List<RegistrationParticipant>> participantsX = new HashMap<>();

    public List<List<RegistrationParticipant>> generateRandomGroups() {
        logger.debug("begin generateRandomGroups");
        System.out.println("begin generateRandomGroups sout");
        //fill the lists with the currently registered members
        List<RegistrationParticipant> registrationParticipants = registrationParticipantRepository.findAllAspis();

        Iterator<RegistrationParticipant> registrationIterator = registrationParticipants.iterator();
        while (registrationIterator.hasNext()) {
            RegistrationParticipant participant = registrationIterator.next();
            this.addParticipantInCorrectGroup(participant);
        }

        //take members from the separate lists and group them
        List<List<RegistrationParticipant>> groupsOfParticipant = new ArrayList<>();

        Boolean stop = false;
        do {
            List<RegistrationParticipant> tempList = assignParticipantsToGroup();
            if (tempList.size() <= 0) {
                stop = true;
            }
            groupsOfParticipant.add(tempList);
        } while (!stop);


        logger.debug("You need to be here");
        for(int i = 0; i < groupsOfParticipant.size(); i++) {
            List<RegistrationParticipant> reglist = groupsOfParticipant.get(i);
            for(int j = 0; j < reglist.size(); j++) {
                logger.debug(reglist.get(j).getFirstName());
            }
        }
        return groupsOfParticipant;
    }

    private List<RegistrationParticipant> assignParticipantsToGroup() {
        List<RegistrationParticipant> tempList = new ArrayList<>();
        List<ChiroUnit> unions = chiroUnitRepository.findAllVerbonden();
        int amountMale = 0;
        int amountFemale = 0;

        //todo fix for GROUPSIZE < union.size
        for (int i = 0; i < unions.size(); i++) {
            String unionName = unions.get(i).getName();

            addParticipantUnionToList(unionName, tempList, amountMale, amountFemale);
        }

        //if union.size < GROUPSIZE
        if (tempList.size() < GROUPSIZE) {
            fillUpGroup(tempList, unions, GROUPSIZE - tempList.size(), amountMale, amountFemale);
        }

        return tempList;
    }

    /*in case groupsize > unions extra members need to be added to the groups*/
    private void fillUpGroup(List<RegistrationParticipant> tempList, List<ChiroUnit> unions, int itemsNeeded, int amountMale, int amountFemale) {
        List<Integer> randomIndexUnions = new ArrayList<>();
        int unionSize = unions.size();

        randomIndexUnions.add((int) Math.random() * unionSize);
        while (randomIndexUnions.size() < itemsNeeded && itemsNeeded < unionSize) { //todo throw exception if itemsNeeded < unionSize
            int index = (int) Math.random() * unionSize;
            while (randomIndexUnions.contains(index)) {
                index = (int) Math.random() * unionSize;
            }
            randomIndexUnions.add(index);
        }

        for (int i = 0; i < randomIndexUnions.size(); i++) {
            String unionName = unions.get(randomIndexUnions.get(i)).getName();

            addParticipantUnionToList(unionName, tempList, amountMale, amountFemale);
        }
    }

    private void addParticipantUnionToList(String unionName, List<RegistrationParticipant> tempList, Integer amountMale, Integer amountFemale) {
        //this will fix the case that GROUPSIZE is not even
        int genderGroupSize = (GROUPSIZE + 1) / 2;

        if (amountMale < genderGroupSize && amountFemale < genderGroupSize) {
            int random = (int) Math.random() * 2;

            if (random == 0) {
                if (!addMaleToList(unionName, tempList)) {
                    addFemaleToList(unionName, tempList);
                }
            } else {
                if (!addFemaleToList(unionName, tempList)) {
                    addMaleToList(unionName, tempList);
                }
            }
        } else if (amountMale >= genderGroupSize) {
            addFemaleToList(unionName, tempList); //TODO edit priorities here
        } else if (amountFemale >= genderGroupSize) {
            addMaleToList(unionName, tempList); //TODO edit priorities here
        }
    }


    private boolean addMaleToList(String unionName, List<RegistrationParticipant> tempList) {
        if(participantsMale != null){
            List<RegistrationParticipant> registeredMalesUnion = participantsMale.get(unionName);
            if (registeredMalesUnion != null && registeredMalesUnion.size() > 0) {
                int randomIndex = (int) (Math.random() * registeredMalesUnion.size());
                tempList.add(registeredMalesUnion.get(randomIndex));
                registeredMalesUnion.remove(randomIndex);
                return true;
            }
        }
        return false;
    }

    private boolean addFemaleToList(String unionName, List<RegistrationParticipant> tempList) {
        if (participantsFemale != null) {
            List<RegistrationParticipant> registeredFemaleUnion = participantsFemale.get(unionName);
            if (registeredFemaleUnion != null && registeredFemaleUnion.size() > 0) {
                int randomIndex = (int) (Math.random() * registeredFemaleUnion.size());
                tempList.add(registeredFemaleUnion.get(randomIndex));
                registeredFemaleUnion.remove(randomIndex);
                return true;
            }
        }
        return false;
    }

    private void addParticipantInCorrectGroup(RegistrationParticipant participant) {
        String union = chiroUnitRepository.findUnionParticipant(participant.getAdNumber());

        if(participant.getGender().equals(Gender.MAN)) {
            addToMap(union, participant, participantsMale);
        } else if(participant.getGender().equals(Gender.WOMAN)) {
            addToMap(union, participant, participantsFemale);
        } else {
            addToMap(union, participant, participantsX);
        }
    }

    private void addToMap(String union, RegistrationParticipant participant, Map<String, List<RegistrationParticipant>> map) {
        if(map.get(union) == null) {
            List<RegistrationParticipant> registrationParticipants = new ArrayList<>();
            registrationParticipants.add(participant);
            map.put(union, registrationParticipants);
        } else {
            map.get(union).add(participant);
        }
    }

    //union = verbond (trying to reduce nenglish but no proper translation)
    /*protected class ParticipantWithUnion {
        private RegistrationParticipant participant;

        private String union;


        public ParticipantWithUnion(RegistrationParticipant participant) {
            this.participant = participant;
            this.union = chiroUnitRepository.findUnionParticipant(participant.getAdNumber());
        }

        public RegistrationParticipant getParticipant() {
            return participant;
        }

        public void setParticipant(RegistrationParticipant participant) {
            this.participant = participant;
        }

        public String getUnion() {
            return union;
        }

        public void setUnion(String union) {
            this.union = union;
        }
    }*/
}
