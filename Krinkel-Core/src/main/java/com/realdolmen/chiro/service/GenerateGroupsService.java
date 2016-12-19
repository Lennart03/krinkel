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

    private static Integer GROUPSIZE = 16;

    private Map<String, List<RegistrationParticipant>> participantsMale = new HashMap<>();
    private Map<String, List<RegistrationParticipant>> participantsFemale = new HashMap<>();
    private Map<String, List<RegistrationParticipant>> participantsX = new HashMap<>();

    private int amountMale = 0;
    private int amountFemale = 0;

    public List<List<RegistrationParticipant>> generateRandomGroups() {
        System.out.println("Generate random groups");
        //detailed//system.out.println("begin generateRandomGroups");
        //fill the lists with the currently registered members
        List<RegistrationParticipant> registrationParticipants = registrationParticipantRepository.findAllAspis();
        System.out.println("amount registrationParticipants " + registrationParticipants.size());
        //detailed//system.out.println("fetched the registrationparticipants");

        Iterator<RegistrationParticipant> registrationIterator = registrationParticipants.iterator();
        //detailed//system.out.println("fetched registrationiterator");
        while (registrationIterator.hasNext()) {
            //detailed//system.out.println("iterator has next");
            RegistrationParticipant participant = registrationIterator.next();
            //detailed//system.out.println("fetched participant next");
            this.addParticipantInCorrectGroup(participant);
            //detailed//system.out.println("added participant in the correct group");
        }

        /*debug code
        Set<String> keyset = participantsMale.keySet();
        Iterator<String> it = keyset.iterator();
        while(it.hasNext()) {
            String next = it.next();
            System.out.println("union: " + next);
            for(int j =0; j < participantsMale.get(next).size(); j++) {
                totalMale++;
                System.out.println(participantsMale.get(next).get(j).getAdNumber());
            }
        }*/

        //detailed//system.out.println("no registration participants left");
        //take members from the separate lists and group them
        List<List<RegistrationParticipant>> groupsOfParticipant = new ArrayList<>();
        //detailed//system.out.println("initialize groups of participant array");

        Boolean stop = false;
        //detailed//system.out.println("make boolean stop");
        do {
            //detailed//system.out.println("enter do");
            List<RegistrationParticipant> tempList = assignParticipantsToGroup();
            /*system.out.println("New tempList:");
            for(int i = 0; i < tempList.size(); i++) {
                sytem.out.println(tempList.get(i).getAdNumber() + "    " + tempList.get(i).getGender());
            }*/
            //detailed//system.out.println("fetched tempList");
            if (tempList.size() <= 0) {
                //detailed//system.out.println("templist is empty");
                stop = true;
            } else {
                //detailed//system.out.println("add the templist to the groups");
                groupsOfParticipant.add(tempList);
            }
        } while (!stop);


        /*System.out.println("the whole group seperation is finished");

        for(int i = 0; i < groupsOfParticipant.size(); i++) {
            List<RegistrationParticipant> reglist = groupsOfParticipant.get(i);
            System.out.println("Group " + i + ":");
            int amountMale = 0;
            int amountFemale = 0;
            for(int j = 0; j < reglist.size(); j++) {
                if(reglist.get(j).getGender().equals(Gender.MAN)) {
                    System.out.println(reglist.get(j).getAdNumber() + "   MAN  " + reglist.get(j).getStamnumber());
                    amountMale++;
                } else if(reglist.get(j).getGender().equals(Gender.WOMAN)) {
                    System.out.println(reglist.get(j).getAdNumber() + "   WOMAN  " + reglist.get(j).getStamnumber());
                    amountFemale++;
                }
            }
            System.out.println("Size group: " + reglist.size());
            System.out.println("Amount male: " + amountMale);
            System.out.println("Amount female: " + amountFemale);
        }*/
        return groupsOfParticipant;
    }

    protected List<RegistrationParticipant> assignParticipantsToGroup() {
        List<RegistrationParticipant> tempList = new ArrayList<>();
        //detailed//system.out.println("enter assignParticipantsToGroup");
        List<ChiroUnit> unions = chiroUnitRepository.findAllVerbonden();

        //detailed//system.out.println("alle verbonden gevonden : " + unions.size());
        
        amountMale = 0;
        amountFemale = 0;

        //todo fix for GROUPSIZE < union.size
        for (int i = 0; i < unions.size(); i++) {
            String unionStam = unions.get(i).getStam();
            //detailed//system.out.println("add participant to union list");
            addParticipantUnionToList(unionStam, tempList);
            //detailed//system.out.println("added participant to union list");
        }

        //if union.size < GROUPSIZE
        if (tempList.size() < GROUPSIZE) {
            //detailed//system.out.println("add participant to union list");
            fillUpGroup(tempList, unions, GROUPSIZE - tempList.size());
            //detailed//system.out.println("after fill up the group");
        }

        //detailed//system.out.println("end assignParticipantsToGroup");
        return tempList;
    }

    /*in case groupsize > unions extra members need to be added to the groups*/
    private void fillUpGroup(List<RegistrationParticipant> tempList, List<ChiroUnit> unions, int itemsNeeded) {
        List<Integer> randomIndexUnions = new ArrayList<>();
        int unionSize = unions.size();

        if(unionSize > 0) {
            randomIndexUnions.add((int) (Math.random() * unionSize));
        }

        int itemsAdded = 0;
        while (itemsAdded < itemsNeeded && randomIndexUnions.size() < unionSize && itemsNeeded < unionSize && unionSize > 0) { //todo throw exception if itemsNeeded < unionSize
            int index = (int) (Math.random() * unionSize);
            while (randomIndexUnions.contains(index)) {
                index = (int) (Math.random() * unionSize);
            }
            randomIndexUnions.add(index);

            String unionStam = unions.get(index).getStam();

            if(addParticipantUnionToList(unionStam, tempList)) {
                itemsAdded++;
            }
        }
    }

    protected boolean addParticipantUnionToList(String unionStam, List<RegistrationParticipant> tempList) {
        //this will fix the case that GROUPSIZE is not even
        int genderGroupSize = (GROUPSIZE + 1) / 2;

        if (amountMale < genderGroupSize && amountFemale < genderGroupSize) {
            int random = (int) (Math.random() * 2);

            if (random == 0) {
                //detailed//system.out.println("random result was adding male to list");
                if (!addMaleToList(unionStam, tempList)) {
                    //detailed//system.out.println("failed to add male to list, trying to add female");
                    if(addFemaleToList(unionStam, tempList)) {
                        return true;
                    }
                } else {
                    return true;
                }
            } else {
                //detailed//system.out.println("random result was adding female to list");
                if (!addFemaleToList(unionStam, tempList)) {
                    //detailed//system.out.println("failed to add female to list, trying to add male");
                    if(addMaleToList(unionStam, tempList)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        } else if (amountMale >= genderGroupSize && amountFemale < genderGroupSize) {
            //detailed//system.out.println("too much males, add female");
            if(addFemaleToList(unionStam, tempList)) { //TODO edit priorities here
                return true;
            }
        } else if (amountFemale >= genderGroupSize && amountMale < genderGroupSize) {
            //detailed//system.out.println("too much females, add male");
            if(addMaleToList(unionStam, tempList)) { //TODO edit priorities here
                return true;
            }
        }

        return false;
    }


    protected boolean addMaleToList(String unionName, List<RegistrationParticipant> tempList) {
        //detailed//system.out.println("add a male to the list");
        if(participantsMale != null){
            //detailed//system.out.println("there are still male participants left");
            List<RegistrationParticipant> registeredMalesUnion = participantsMale.get(unionName);
            //detailed//system.out.println("got list of males of the selected union");
            if (registeredMalesUnion != null && registeredMalesUnion.size() > 0) {
                //detailed//system.out.println("there are still males left that belong to the union");
                int randomIndex = (int) (Math.random() * registeredMalesUnion.size());
                //detailed//system.out.println("select a random male");
                tempList.add(registeredMalesUnion.get(randomIndex));
                //detailed//system.out.println("add the male to the union");
                registeredMalesUnion.remove(randomIndex);
                //detailed//system.out.println("remove the male from the global list");
                amountMale++;
                return true;
            }
        }
        //detailed//system.out.println("failed to find a male to add");
        return false;
    }

    protected boolean addFemaleToList(String unionStam, List<RegistrationParticipant> tempList) {
        //detailed//system.out.println("add a female to the list");
        if (participantsFemale != null) {
            //detailed//system.out.println("there are still female participants left");
            List<RegistrationParticipant> registeredFemaleUnion = participantsFemale.get(unionStam);
            //detailed//system.out.println("got list of females of the selected union");
            if (registeredFemaleUnion != null && registeredFemaleUnion.size() > 0) {
                //detailed//system.out.println("there are still females left that belong to the union");
                int randomIndex = (int) (Math.random() * registeredFemaleUnion.size());
                //detailed//system.out.println("select a random female");
                tempList.add(registeredFemaleUnion.get(randomIndex));
                //detailed//system.out.println("add the female to the union");
                registeredFemaleUnion.remove(randomIndex);
                //detailed//system.out.println("remove the female from the global list");
                amountFemale++;
                return true;
            }
        }
        //detailed//system.out.println("failed to find a female to add");
        return false;
    }

    protected void addParticipantInCorrectGroup(RegistrationParticipant participant) {
        //detailed//system.out.println("find union participant");
        String union = chiroUnitRepository.findUnionParticipant(participant.getAdNumber());
        //detailed//system.out.println("union participant found");

        if(participant.getGender().equals(Gender.MAN)) {
            //detailed//system.out.println("the participant was male");
            addToMap(union, participant, participantsMale);
        } else if(participant.getGender().equals(Gender.WOMAN)) {
            //detailed//system.out.println("the participant was female");
            addToMap(union, participant, participantsFemale);
        } else {
            //detailed//system.out.println("the participant is something else");
            addToMap(union, participant, participantsX);
        }
    }

    private void addToMap(String union, RegistrationParticipant participant, Map<String, List<RegistrationParticipant>> map) {
        //detailed//system.out.println("enter addToMap");
        if(map.get(union) == null) {
            //detailed//system.out.println("the map of the union is empty");
            List<RegistrationParticipant> registrationParticipants = new ArrayList<>();
            //detailed//system.out.println("make now registrationparticipants list");
            registrationParticipants.add(participant);
            //detailed//system.out.println("added the participant to the list");
            map.put(union, registrationParticipants);
            //detailed//system.out.println("put the participants list in the map");
        } else {
            //detailed//system.out.println("the map of the union is not empty");
            map.get(union).add(participant);
            //detailed//system.out.println("add the participant to the map");
        }
    }

    protected Map<String, List<RegistrationParticipant>> getParticipantsFemale() {
        return participantsFemale;
    }

    protected Map<String, List<RegistrationParticipant>> getParticipantsMale() {
        return participantsMale;
    }

    protected Map<String, List<RegistrationParticipant>> getParticipantsX() {
        return participantsX;
    }

    public static Integer getGROUPSIZE() {
        return GROUPSIZE;
    }

    protected void setParticipantsFemale(Map<String, List<RegistrationParticipant>> participantsFemale) {
        this.participantsFemale = participantsFemale;
    }

    protected void setParticipantsMale(Map<String, List<RegistrationParticipant>> participantsMale) {
        this.participantsMale = participantsMale;
    }

    protected void setParticipantsX(Map<String, List<RegistrationParticipant>> participantsX) {
        this.participantsX = participantsX;
    }

    protected int getAmountFemale() {
        return amountFemale;
    }

    protected int getAmountMale() {
        return amountMale;
    }

    protected void setAmountFemale(int amountFemale) {
        this.amountFemale = amountFemale;
    }

    protected void setAmountMale(int amountMale) {
        this.amountMale = amountMale;
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
