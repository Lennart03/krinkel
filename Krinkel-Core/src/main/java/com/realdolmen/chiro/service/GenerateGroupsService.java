package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialRef;
import java.util.*;

/**
 * Created by MHSBB71 on 15/12/2016.
 */
@Service
public class GenerateGroupsService {
    @Autowired
    private ChiroUnitRepository chiroUnitRepository;

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    private int groupSize = 12;
    private int maleGroupSize = (groupSize + 1) / 2; //standard groupsize
    private int femaleGroupSize = (groupSize + 1) / 2;

    private Map<String, List<RegistrationParticipant>> participantsMan = new HashMap<>();
    private Map<String, List<RegistrationParticipant>> participantsWoman = new HashMap<>();
    //private Map<String, List<RegistrationParticipant>> participantsX = new HashMap<>();

    private int amountMan = 0;
    private int amountWoman = 0;

    public List<List<RegistrationParticipant>> generateRandomGroups() {
        return this.generateRandomGroups(12, -1);
    }

    public List<List<RegistrationParticipant>> generateRandomGroups(int groupSize, int option) {
        this.groupSize = groupSize;
        //fill the lists with the currently registered members
        List<RegistrationParticipant> registrationParticipants = registrationParticipantRepository.findAllParticipantsNoBuddy();

        for(RegistrationParticipant participant : registrationParticipants) {
            this.addParticipantInCorrectGroup(participant);
        }

        //determine the genderGroupSize depending on the amount of male/female members
        determineGenderGroupSizes();

        //take members from the separate lists and group them
        List<List<RegistrationParticipant>> groupsOfParticipant = new ArrayList<>();

        boolean stop = false;
        List<ChiroUnit> unions = chiroUnitRepository.findAllVerbonden();
        do {
            List<RegistrationParticipant> singleGroup = assignParticipantsToGroup(unions);

            if (singleGroup.size() <= 0) {
                stop = true;
            } else {
                groupsOfParticipant.add(singleGroup);
            }
        } while (!stop);

        switch(option) {
            case 1:
                scrambleInRestGroup(groupsOfParticipant);
                break;
            case 0:
                combineNotFullGroups(groupsOfParticipant);
                break;
        }

        return groupsOfParticipant;
    }

    private void determineGenderGroupSizes() {
        double totalMale = 0;
        double totalFemale = 0;

        Set<String> keys = participantsMan.keySet();
        for(String key : keys) {
            totalMale += participantsMan.get(key).size();
        }

        keys = participantsWoman.keySet();
        for(String key : keys) {
            totalFemale += participantsWoman.get(key).size();
        }

        double total = totalMale + totalFemale;

        this.maleGroupSize = (int) ((totalMale / total) * groupSize + 0.5);
        this.femaleGroupSize = (int) ((totalFemale / total) * groupSize + 0.5);
    }


    protected List<RegistrationParticipant> assignParticipantsToGroup(List<ChiroUnit> unions) {
        List<RegistrationParticipant> singleGroup = new ArrayList<>();

        amountMan = 0;
        amountWoman = 0;

        if (unions.size() <= groupSize) {
            for (ChiroUnit union : unions) {
                addParticipantUnionToList(union.getStamNummer(), singleGroup);
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
/*
        if(unionSize > 0) {
            randomIndexUnions.add((int) (Math.random() * unionSize));
        }
*/
        int itemsAdded = 0;
        String unionStam;
        //loop taking genders into account
        while (itemsAdded < itemsNeeded && unionSize > 0 && randomIndexUnions.size() < unionSize) {
            int index = (int) (Math.random() * unionSize);

            unionStam = unions.get(index).getStamNummer();
            if (!randomIndexUnions.contains(index)) {
                if (!participantLeftInUnion(unionStam)) {
                    randomIndexUnions.add(index);
                } else if (addParticipantUnionToList(unionStam, singleGroup)) {
                    itemsAdded++;
                } else {
                    randomIndexUnions.add(index);
                }
            }
        }

        randomIndexUnions.clear();
        //loop discarding gender restriction
        while (itemsAdded < itemsNeeded && unionSize > 0 && randomIndexUnions.size() < unionSize) {
            int index = (int) (Math.random() * unionSize);

            unionStam = unions.get(index).getStamNummer();
            if(!randomIndexUnions.contains(index)) {
                if (!participantLeftInUnion(unionStam)) {
                    randomIndexUnions.add(index);
                } else if (addParticipantUnionToListNoGenderRestriction(unionStam, singleGroup)) {
                    itemsAdded++;
                } else {
                    randomIndexUnions.add(index);
                }
            }
        }
    }

    protected boolean participantLeftInUnion(String unionStam) {
        return (!(this.participantsMan.get(unionStam) == null) && this.participantsMan.get(unionStam).size() > 0)
                || (!(this.participantsWoman.get(unionStam) == null) && this.participantsWoman.get(unionStam).size() > 0);
    }

    protected boolean OnlyOneOrNoGenderLeftInUnion(String unionStam) {
        return (!(this.participantsMan.get(unionStam) == null) && this.participantsMan.get(unionStam).size() > 0)
                && (!(this.participantsWoman.get(unionStam) == null) && this.participantsWoman.get(unionStam).size() > 0);
    }

    protected boolean addParticipantUnionToList(String unionStam, List<RegistrationParticipant> singleGroup) {
        //this will fix the case that groupSize is not even
        //genderGroupSize = (groupSize + 1) / 2;

        if (amountMan < maleGroupSize && amountWoman < femaleGroupSize) {
            int random = (int) (Math.random() * 2);

            if (random == 0) {
                return addManToList(unionStam, singleGroup) || addWomanToList(unionStam, singleGroup);
            } else {
                return addWomanToList(unionStam, singleGroup) || addManToList(unionStam, singleGroup);
            }
        } else if (amountMan >= maleGroupSize && amountWoman < femaleGroupSize) {
            return addWomanToList(unionStam, singleGroup);
        } else if (amountWoman >= femaleGroupSize && amountMan < maleGroupSize) {
            return addManToList(unionStam, singleGroup);
        }

        return false;
    }

    protected boolean addParticipantUnionToListNoGenderRestriction(String unionStam, List<RegistrationParticipant> singleGroup) {
        int random = (int) (Math.random() * 2);

        if (random == 0)
            return addManToList(unionStam, singleGroup) || addWomanToList(unionStam, singleGroup);

        return addWomanToList(unionStam, singleGroup) || addManToList(unionStam, singleGroup);
    }

    protected boolean addManToList(String unionName, List<RegistrationParticipant> singleGroup) {
        if(addMemberToList(unionName, singleGroup, participantsMan)) {
            amountMan++;
            return true;
        }
        return false;
    }

    protected boolean addWomanToList(String unionName, List<RegistrationParticipant> singleGroup) {
        if(addMemberToList(unionName, singleGroup, participantsWoman)) {
            amountWoman++;
            return true;
        }
        return false;
    }

    private boolean addMemberToList(String unionName, List<RegistrationParticipant> singleGroup, Map<String, List<RegistrationParticipant>> participants) {
        if(participants != null){
            List<RegistrationParticipant> participantsUnion = participants.get(unionName);
            if (participantsUnion != null && participantsUnion.size() > 0) {
                List<Integer> selectedParticipants = new ArrayList<>();
                while (selectedParticipants.size() < participantsUnion.size()) {
                    int randomIndex = (int) (Math.random() * participantsUnion.size());

                    if (!selectedParticipants.contains(randomIndex)) {
                        selectedParticipants.add(randomIndex);
                        RegistrationParticipant participant = participantsUnion.get(randomIndex);

                        if (!twoMembersInOneGroup(participant, singleGroup)) {//two members can't be in the same group
                            //if a member found that doesn't break any condition add him/her to the group
                            singleGroup.add(participant);
                            participantsUnion.remove(randomIndex);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected boolean twoMembersInOneGroup(RegistrationParticipant participant, List<RegistrationParticipant> singleGroup) {
        for(RegistrationParticipant member : singleGroup) {
            if(participant.getStamnumber().equals(member.getStamnumber())) {
                return true;
            }
        }
        return false;
    }

    protected void addParticipantInCorrectGroup(RegistrationParticipant participant) {
        /*ChiroUnit unit = chiroUnitRepository.findUnionParticipant(adNumber);
        if(unit == null) {
            System.err.println("unit is null");
        }
        String unionName = unit.getStamNummer();*/

        String unionName = chiroUnitRepository.findUnionUsingAD(participant.getAdNumber());
        if(unionName == null || unionName == "") {
           unionName = "Other";
        }

        if (participant.getGender().equals(Gender.MAN)) {
            addToMap(unionName, participant, participantsMan);
        } else if (participant.getGender().equals(Gender.WOMAN)) {
            addToMap(unionName, participant, participantsWoman);
        } else { //scrumble gender X participants in random male/female groups
            if ((int) (Math.random() * 2) > 0) {
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

            WrapInt wrapAmountMan = new WrapInt();
            WrapInt wrapAmountWoman = new WrapInt();

            countGenders(singleGroup, wrapAmountMan, wrapAmountWoman);
            int amountMan = wrapAmountMan.value;
            int amountWoman = wrapAmountWoman.value;

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

    /*
    * public function to put behind a button
     */
    public List<List<RegistrationParticipant>> scrambleTheRestGroup(List<List<RegistrationParticipant>> groupsOfParticipants) {
        this.scrambleInRestGroup(groupsOfParticipants);
        return groupsOfParticipants;
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
        WrapInt amountMan = new WrapInt();
        WrapInt amountWoman = new WrapInt();
        countGenders(singleGroup, amountMan, amountWoman);

        if(amountWoman.value < amountMan.value - 1 || amountWoman.value > amountMan.value + 1){//the scramble function will not allow more than 1 gender offset
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

    private class WrapInt {
        public int value = 0;
    }


    private void countGenders(List<RegistrationParticipant> singleGroup, WrapInt amountMan, WrapInt amountWoman) {
        for (RegistrationParticipant participant : singleGroup) {
            if (participant.getGender().equals(Gender.MAN)) {
                amountMan.value++;
            } else if (participant.getGender().equals(Gender.WOMAN)) {
                amountWoman.value++;
            }
        }
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

    /*
    * put all the groups with a size smaller then groupsize into 1 group so they can be manually sorted later on
    * */
    private void combineNotFullGroups(List<List<RegistrationParticipant>> groupsOfParticipants) {
        int i;
        //only the last group should be smaller than the max groupsize
        for(i = groupsOfParticipants.size() - 1; i > 0 && groupsOfParticipants.get(i).size() < groupSize; i--) {
        }
        i++;

        List<RegistrationParticipant> restGroup = new ArrayList<>();
        while(i > 0 && i < groupsOfParticipants.size()) {
            restGroup.addAll(groupsOfParticipants.get(i));
            groupsOfParticipants.remove(i);
        }

        if(restGroup.size() > 0) {
            groupsOfParticipants.add(restGroup);
        }
    }

    /*
    * public function to put behind a button
     */
    public List<List<RegistrationParticipant>> combineRestGroups(List<List<RegistrationParticipant>> groupsOfParticipants) {
        this.combineNotFullGroups(groupsOfParticipants);
        return groupsOfParticipants;
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
