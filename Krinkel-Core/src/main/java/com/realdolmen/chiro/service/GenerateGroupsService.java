package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
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

    private Integer groupSize = 12;

    private Map<String, List<RegistrationParticipant>> participantsMan = new HashMap<>();
    private Map<String, List<RegistrationParticipant>> participantsWoman = new HashMap<>();
    //private Map<String, List<RegistrationParticipant>> participantsX = new HashMap<>();

    private int amountMan = 0;
    private int amountWoman = 0;

    public List<List<RegistrationParticipant>> generateRandomGroups() {
        return this.generateRandomGroups(12);
    }

    public List<List<RegistrationParticipant>> generateRandomGroups(int groupSize) {
        System.out.println("groupsize " + groupSize);
        this.groupSize = groupSize;
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
            List<RegistrationParticipant> singleGroup = assignParticipantsToGroup();

            if (singleGroup.size() <= 0) {
                stop = true;
            } else {
                groupsOfParticipant.add(singleGroup);
            }
        } while (!stop);


        /*System.out.println("the whole group seperation is finished");

        for(int i = 0; i < groupsOfParticipant.size(); i++) {
            List<RegistrationParticipant> reglist = groupsOfParticipant.get(i);
            System.out.println("Group " + i + ":");
            int amountMan = 0;
            int amountWoman = 0;
            for(int j = 0; j < reglist.size(); j++) {
                if(reglist.get(j).getGender().equals(Gender.MAN)) {
                    System.out.println(reglist.get(j).getAdNumber() + "   MAN  " + reglist.get(j).getStamnumber());
                    amountMan++;
                } else if(reglist.get(j).getGender().equals(Gender.WOMAN)) {
                    System.out.println(reglist.get(j).getAdNumber() + "   WOMAN  " + reglist.get(j).getStamnumber());
                    amountWoman++;
                }
            }
            System.out.println("Size group: " + reglist.size());
            System.out.println("Amount male: " + amountMan);
            System.out.println("Amount female: " + amountWoman);
        }*/

        scrambleInRestGroup(groupsOfParticipant);

        return groupsOfParticipant;
    }

    protected List<RegistrationParticipant> assignParticipantsToGroup() {
        List<RegistrationParticipant> singleGroup = new ArrayList<>();
        List<ChiroUnit> unions = chiroUnitRepository.findAllVerbonden();

        amountMan = 0;
        amountWoman = 0;

        if (unions.size() <= groupSize) {
            for (int i = 0; i < unions.size(); i++) {
                String unionStam = unions.get(i).getStamNummer();
                addParticipantUnionToList(unionStam, singleGroup);
            }

            if(singleGroup.size() < groupSize) {//in case union.size < groupSize
                fillUpGroup(singleGroup, unions, groupSize - singleGroup.size());
            }
        } else {
            List<Integer> selectedUnions = new ArrayList<>();
            for (int i = 0; i < groupSize;) {
                //select random union
                int randNumber = (int) (Math.random() * unions.size());
                while(selectedUnions.contains(randNumber) && selectedUnions.size() < unions.size()){
                    randNumber = (int) (Math.random() * unions.size());
                }
                selectedUnions.add(randNumber);

                String unionStam = unions.get(randNumber).getStamNummer();

                if(!addParticipantUnionToList(unionStam, singleGroup)) {
                    if(selectedUnions.size() >= unions.size()) {
                        break; //if no other unions that can help add members stop looping
                    }
                } else {
                    i++; //only increase when a member is added to the group
                }
            }

            if(singleGroup.size() < groupSize) {//in case there were not enough members of specific unions
                fillUpGroup(singleGroup, unions, groupSize - singleGroup.size());
            }
        }

        return singleGroup;
    }

    /*in case groupsize > unions extra members need to be added to the groups*/
    private void fillUpGroup(List<RegistrationParticipant> singleGroup, List<ChiroUnit> unions, int itemsNeeded) {
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

            String unionStam = unions.get(index).getStamNummer();

            if(addParticipantUnionToList(unionStam, singleGroup)) {
                itemsAdded++;
            }
        }
    }

    protected boolean addParticipantUnionToList(String unionStam, List<RegistrationParticipant> singleGroup) {
        //this will fix the case that groupSize is not even
        int genderGroupSize = (groupSize + 1) / 2;

        if (amountMan < genderGroupSize && amountWoman < genderGroupSize) {
            int random = (int) (Math.random() * 2);

            if (random == 0) {
                if (!addManToList(unionStam, singleGroup)) {
                    if(addWomanToList(unionStam, singleGroup)) {
                        return true;
                    }
                } else {
                    return true;
                }
            } else {
                if (!addWomanToList(unionStam, singleGroup)) {
                    if(addManToList(unionStam, singleGroup)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        } else if (amountMan >= genderGroupSize && amountWoman < genderGroupSize) {
            if(addWomanToList(unionStam, singleGroup)) { //TODO edit priorities here
                return true;
            }
        } else if (amountWoman >= genderGroupSize && amountMan < genderGroupSize) {
            if(addManToList(unionStam, singleGroup)) { //TODO edit priorities here
                return true;
            }
        }

        return false;
    }


    protected boolean addManToList(String unionName, List<RegistrationParticipant> singleGroup) {
        if(participantsMan != null){
            List<RegistrationParticipant> registeredMansUnion = participantsMan.get(unionName);
            if (registeredMansUnion != null && registeredMansUnion.size() > 0) {
                int randomIndex = (int) (Math.random() * registeredMansUnion.size());

                singleGroup.add(registeredMansUnion.get(randomIndex));
                registeredMansUnion.remove(randomIndex);

                amountMan++;
                return true;
            }
        }
        return false;
    }

    protected boolean addWomanToList(String unionStam, List<RegistrationParticipant> singleGroup) {
        if (participantsWoman != null) {
            List<RegistrationParticipant> registeredWomanUnion = participantsWoman.get(unionStam);

            if (registeredWomanUnion != null && registeredWomanUnion.size() > 0) {
                int randomIndex = (int) (Math.random() * registeredWomanUnion.size());

                singleGroup.add(registeredWomanUnion.get(randomIndex));
                registeredWomanUnion.remove(randomIndex);

                amountWoman++;
                return true;
            }
        }
        return false;
    }

    protected void addParticipantInCorrectGroup(RegistrationParticipant participant) {
        String unionName = chiroUnitRepository.findUnionParticipant(participant.getAdNumber()).getStamNummer();

        if(participant.getGender().equals(Gender.MAN)) {
            addToMap(unionName, participant, participantsMan);
        } else if(participant.getGender().equals(Gender.WOMAN)) {
            addToMap(unionName, participant, participantsWoman);
        } else { //scrumble gender X participants in random male/female groups
            if((int)(Math.random() * 2) > 0) {
                addToMap(unionName, participant, participantsMan);
            } else {
                addToMap(unionName, participant, participantsWoman);
            }
            //addToMap(union, participant, participantsX);
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
    /*
    * In the final groups there might be more than 1 group with less members than the set groupsize
    * It might also happen that the group filled with half the groupsize of only one gender
    * This is all a result of the implemented restrictions, to counter this the scramble function will
    * scramble the remaining members into the complete groups.
     */
    private void scrambleInRestGroup(List<List<RegistrationParticipant>> groupsOfParticipants) {
        int i;
        //only the last group should be smaller than the max groupsize
        for(i = groupsOfParticipants.size() - 1; i > 0 && groupsOfParticipants.get(i).size() < groupSize; i--) {
        }
        i++;

        /*
        * add fill up groups if there are more than one group with size < groupSize
         */
        for(int p = i; p+1 < groupsOfParticipants.size(); p++) {
            while(p+1 < groupsOfParticipants.size() //because groupsofparticipants size can become smaller
                    //&& q < groupsOfParticipants.get(p+1).size() //cause you will loop over the list you will empty
                    && groupsOfParticipants.get(p).size() < groupSize//the list you are filling up can't become bigger than the groupsize
                    ) {
                groupsOfParticipants.get(p).add(groupsOfParticipants.get(p + 1).remove(0));
                if(groupsOfParticipants.get(p+1).size() <= 0) {
                    groupsOfParticipants.remove(p + 1);
                }
            }
        }

        for(;i < groupsOfParticipants.size(); i++){
            List<RegistrationParticipant> singleGroup = groupsOfParticipants.get(i);

            int amountMan = 0;
            int amountWoman = 0;
            for (int j = 0; j < singleGroup.size(); j++) {
                if (singleGroup.get(j).getGender().equals(Gender.MAN)) {
                    amountMan++;
                } else if (singleGroup.get(j).getGender().equals(Gender.WOMAN)) {
                    amountWoman++;
                }
            }


            for(int index = 0; index < singleGroup.size(); index++) {
                if(amountMan > amountWoman) {
                    if (scrambleWithWoman(singleGroup, index, groupsOfParticipants, i)) {
                        amountWoman++;
                        amountMan--;
                    }
                } else if(amountWoman > amountMan) {
                    if(scrambleWithMan(singleGroup, index, groupsOfParticipants, i)) {
                        amountMan++;
                        amountWoman--;
                    }
                } else {
                    if(singleGroup.get(index).getGender().equals(Gender.MAN)) {
                        if(!scrambleWithMan(singleGroup, index, groupsOfParticipants, i)) {
                            if(scrambleWithWoman(singleGroup, index, groupsOfParticipants, i)) {
                                amountMan--;
                                amountWoman++;
                            }
                        }
                    } else if(singleGroup.get(index).getGender().equals(Gender.WOMAN)) {
                        if(!scrambleWithWoman(singleGroup, index, groupsOfParticipants, i)) {
                            if(scrambleWithMan(singleGroup, index, groupsOfParticipants, i)) {
                                amountMan++;
                                amountWoman--;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean scrambleWithMan(List<RegistrationParticipant> singleGroup, int index, List<List<RegistrationParticipant>> groupsOfParticipants, int posList) {
        return scrambleWithPerson(Gender.MAN, singleGroup, index, groupsOfParticipants, posList);
    }

    private boolean scrambleWithWoman(List<RegistrationParticipant> singleGroup, int index, List<List<RegistrationParticipant>> groupsOfParticipants, int posList) {
        return scrambleWithPerson(Gender.WOMAN, singleGroup, index, groupsOfParticipants, posList);
    }

    private boolean scrambleWithPerson(Gender gender, List<RegistrationParticipant> singleGroup, int index, List<List<RegistrationParticipant>> groupsOfParticipants, int posList) {
        for(int i=0; i < groupsOfParticipants.size() && i < posList; i++) {
            if (checkScramblePossible(groupsOfParticipants.get(i), singleGroup.get(index))) {
                for(int j = 0; j < groupsOfParticipants.get(i).size(); j++) {
                    if(groupsOfParticipants.get(i).get(j).getGender().equals(gender) && checkScrambleLackingList(singleGroup, groupsOfParticipants.get(i).get(j))) {
                        RegistrationParticipant tempSG = singleGroup.get(index);
                        RegistrationParticipant tempGOP = groupsOfParticipants.get(i).get(j);
                        singleGroup.remove(index);
                        singleGroup.add(tempGOP);
                        groupsOfParticipants.get(i).remove(j);
                        groupsOfParticipants.get(i).add(tempSG);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
    * This function will check if it is allowed to scramble a member inside of a group
     */
    private boolean checkScramblePossible(List<RegistrationParticipant> singleGroup, RegistrationParticipant participant) {
        int amountMan = 0;
        int amountWoman = 0;
        for (int i = 0; i < singleGroup.size(); i++) {
            if (singleGroup.get(i).getGender().equals(Gender.MAN)) {
                amountMan++;
            } else if (singleGroup.get(i).getGender().equals(Gender.WOMAN)) {
                amountWoman++;
            }
        }

        if(amountWoman < amountMan - 1 || amountWoman > amountMan + 1){//the scramble function will not allow more than 1 gender offset
            return false;
        } else {
            String unionParticipant = chiroUnitRepository.findUnionParticipant(participant.getAdNumber()).getStamNummer();
            boolean foundOnce = false;
            for(int i = 0; i < singleGroup.size(); i++) {
                if(unionParticipant.equals(chiroUnitRepository.findUnionParticipant(singleGroup.get(i).getAdNumber()))) {
                    if(foundOnce) {
                        return false;
                    } else {
                        foundOnce = true;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkScrambleLackingList(List<RegistrationParticipant> lackingList, RegistrationParticipant participant) {
        String unionParticipant = chiroUnitRepository.findUnionParticipant(participant.getAdNumber()).getStamNummer();
        boolean foundOnce = false;
        for(int i = 0; i < lackingList.size(); i++) {
            if(unionParticipant.equals(chiroUnitRepository.findUnionParticipant(lackingList.get(i).getAdNumber()))) {
                if(foundOnce) {
                    return false;
                } else {
                    foundOnce = true;
                }
            }
        }

        return true;
    }


    protected Map<String, List<RegistrationParticipant>> getParticipantsWoman() {
        return participantsWoman;
    }

    protected Map<String, List<RegistrationParticipant>> getParticipantsMan() {
        return participantsMan;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    protected void setParticipantsWoman(Map<String, List<RegistrationParticipant>> participantsWoman) {
        this.participantsWoman = participantsWoman;
    }

    protected void setParticipantsMan(Map<String, List<RegistrationParticipant>> participantsMan) {
        this.participantsMan = participantsMan;
    }

    protected int getAmountWoman() {
        return amountWoman;
    }

    protected int getAmountMan() {
        return amountMan;
    }

    protected void setAmountWoman(int amountWoman) {
        this.amountWoman = amountWoman;
    }

    protected void setAmountMan(int amountMan) {
        this.amountMan = amountMan;
    }
}
