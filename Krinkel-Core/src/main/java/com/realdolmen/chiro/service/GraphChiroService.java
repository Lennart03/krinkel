package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.EventRole;
import com.realdolmen.chiro.domain.LoginLog;
import com.realdolmen.chiro.domain.Verbond;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.GraphChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.domain.units.StatusChiroUnit;
import com.realdolmen.chiro.repository.ChiroUnitRepository;
import com.realdolmen.chiro.repository.LoginLoggerRepository;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;
import com.realdolmen.chiro.util.StamNumberTrimmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraphChiroService {
    @Autowired
    private ChiroUnitRepository chiroUnitRepository;

    @Autowired
    private RegistrationParticipantRepository registrationParticipantRepository;

    @Autowired
    private RegistrationParticipantService registrationParticipantService;

    @Autowired
    private LoginLoggerRepository loginLoggerRepository;

    @Autowired
    private StamNumberTrimmer stamNumberTrimmer;

    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToMakeStatusGraph()")
    public StatusChiroUnit getStatusChiro(String name,Integer depth) {
        StatusChiroUnit status = new StatusChiroUnit();
        if(name.equals("null") || name.equals("Inschrijvingen")) {
            registrationParticipantRepository.findAll().forEach(r -> {
                if (r.getEventRole().equals(EventRole.VOLUNTEER)) {
                    switch (r.getStatus()) {
                        case PAID:
                            status.setVolunteersNotConfirmed(status.getVolunteersNotConfirmed() + 1);
                            break;
                        case CONFIRMED:
                            status.setVolunteersConfirmed(status.getVolunteersConfirmed() + 1);
                            break;
                        case TO_BE_PAID:
                            status.setVolunteersNotPaid(status.getVolunteersNotPaid() + 1);
                            break;
                        case CANCELLED:
                            status.setVolunteersCancelled(status.getVolunteersCancelled() + 1);
                            break;
                    }
                } else {
                    switch (r.getStatus()) {
                        case PAID:
                            status.setParticipantsNotConfirmed(status.getParticipantsNotConfirmed() + 1);
                            break;
                        case CONFIRMED:
                            status.setParticipantsConfirmed(status.getParticipantsConfirmed() + 1);
                            break;
                        case TO_BE_PAID:
                            status.setParticipantsNotPaid(status.getParticipantsNotPaid() + 1);
                            break;
                        case CANCELLED:
                            status.setParticipantsCancelled(status.getParticipantsCancelled() + 1);
                            break;
                    }
                }

            });
        }
        else{
            switch(depth)
            {
                case 1:
                    registrationParticipantRepository.findAllParticipantsWithVerbondName(name).forEach(r -> {
                        if (r.getEventRole().equals(EventRole.VOLUNTEER)) {
                            switch (r.getStatus()) {
                                case PAID:
                                    status.setVolunteersNotConfirmed(status.getVolunteersNotConfirmed() + 1);
                                    break;
                                case CONFIRMED:
                                    status.setVolunteersConfirmed(status.getVolunteersConfirmed() + 1);
                                    break;
                                case TO_BE_PAID:
                                    status.setVolunteersNotPaid(status.getVolunteersNotPaid() + 1);
                                    break;
                                case CANCELLED:
                                    status.setVolunteersCancelled(status.getVolunteersCancelled() + 1);
                                    break;
                            }
                        } else {
                            switch (r.getStatus()) {
                                case PAID:
                                    status.setParticipantsNotConfirmed(status.getParticipantsNotConfirmed() + 1);
                                    break;
                                case CONFIRMED:
                                    status.setParticipantsConfirmed(status.getParticipantsConfirmed() + 1);
                                    break;
                                case TO_BE_PAID:
                                    status.setParticipantsNotPaid(status.getParticipantsNotPaid() + 1);
                                    break;
                                case CANCELLED:
                                    status.setParticipantsCancelled(status.getParticipantsCancelled() + 1);
                                    break;
                            }
                        }

                    });
                    break;
                case 2:
                    registrationParticipantRepository.findAllParticipantsWithGewestName(name).forEach(r -> {
                        if (r.getEventRole().equals(EventRole.VOLUNTEER)) {
                            switch (r.getStatus()) {
                                case PAID:
                                    status.setVolunteersNotConfirmed(status.getVolunteersNotConfirmed() + 1);
                                    break;
                                case CONFIRMED:
                                    status.setVolunteersConfirmed(status.getVolunteersConfirmed() + 1);
                                    break;
                                case TO_BE_PAID:
                                    status.setVolunteersNotPaid(status.getVolunteersNotPaid() + 1);
                                    break;
                                case CANCELLED:
                                    status.setVolunteersCancelled(status.getVolunteersCancelled() + 1);
                                    break;
                            }
                        } else {
                            switch (r.getStatus()) {
                                case PAID:
                                    status.setParticipantsNotConfirmed(status.getParticipantsNotConfirmed() + 1);
                                    break;
                                case CONFIRMED:
                                    status.setParticipantsConfirmed(status.getParticipantsConfirmed() + 1);
                                    break;
                                case TO_BE_PAID:
                                    status.setParticipantsNotPaid(status.getParticipantsNotPaid() + 1);
                                    break;
                                case CANCELLED:
                                    status.setParticipantsCancelled(status.getParticipantsCancelled() + 1);
                                    break;
                            }
                        }

                    });
                    break;
                case 3:
                    registrationParticipantRepository.findAllParticipantsWithGroepName(name).forEach(r -> {
                        if (r.getEventRole().equals(EventRole.VOLUNTEER)) {
                            switch (r.getStatus()) {
                                case PAID:
                                    status.setVolunteersNotConfirmed(status.getVolunteersNotConfirmed() + 1);
                                    break;
                                case CONFIRMED:
                                    status.setVolunteersConfirmed(status.getVolunteersConfirmed() + 1);
                                    break;
                                case TO_BE_PAID:
                                    status.setVolunteersNotPaid(status.getVolunteersNotPaid() + 1);
                                    break;
                                case CANCELLED:
                                    status.setVolunteersCancelled(status.getVolunteersCancelled() + 1);
                                    break;
                            }
                        } else {
                            switch (r.getStatus()) {
                                case PAID:
                                    status.setParticipantsNotConfirmed(status.getParticipantsNotConfirmed() + 1);
                                    break;
                                case CONFIRMED:
                                    status.setParticipantsConfirmed(status.getParticipantsConfirmed() + 1);
                                    break;
                                case TO_BE_PAID:
                                    status.setParticipantsNotPaid(status.getParticipantsNotPaid() + 1);
                                    break;
                                case CANCELLED:
                                    status.setParticipantsCancelled(status.getParticipantsCancelled() + 1);
                                    break;
                            }
                        }

                    });
                    break;
            }


        }

        System.out.println(status.toString());
        System.out.println(status.getParticipantsCancelled()+status.getParticipantsConfirmed()+status.getParticipantsNotConfirmed()+status.getParticipantsNotPaid());
        return status;
    }




    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToMakeSunGraph()")
    public GraphChiroUnit summary() {
        GraphChiroUnit root = new GraphChiroUnit("Inschrijvingen", null, new ArrayList<GraphChiroUnit>());

        List<RawChiroUnit> allChiroUnits = findAllUnitsWithRegisteredParticipants();

        for (RawChiroUnit chiroUnit : allChiroUnits) {
            //check if verbond exists
            if (getGraphChiroUnitByLowerUnitName(root.getChildren(), chiroUnit.getVerbondNaam()) == null) {
                GraphChiroUnit verbond = new GraphChiroUnit(chiroUnit.getVerbondNaam(), null, new ArrayList<GraphChiroUnit>());
                GraphChiroUnit gewest = new GraphChiroUnit(chiroUnit.getGewestNaam(), null, new ArrayList<GraphChiroUnit>());
                GraphChiroUnit groep = new GraphChiroUnit(chiroUnit.getGroepNaam(), findParticipants(chiroUnit.getGroepStamNummer()), null);

                gewest.getChildren().add(groep);
                verbond.getChildren().add(gewest);
                root.getChildren().add(verbond);
            } else {
                GraphChiroUnit verbond = getGraphChiroUnitByLowerUnitName(root.getChildren(), chiroUnit.getVerbondNaam());
                //check if gewest exists
                if (getGraphChiroUnitByLowerUnitName(verbond.getChildren(), chiroUnit.getGewestNaam()) == null) {
                    GraphChiroUnit gewest = new GraphChiroUnit(chiroUnit.getGewestNaam(), null, new ArrayList<GraphChiroUnit>());
                    GraphChiroUnit groep = new GraphChiroUnit(chiroUnit.getGroepNaam(), findParticipants(chiroUnit.getGroepStamNummer()), null);

                    gewest.getChildren().add(groep);
                    verbond.getChildren().add(gewest);
                } else {
                    GraphChiroUnit verbondForGroep = getGraphChiroUnitByLowerUnitName(root.getChildren(), chiroUnit.getVerbondNaam());
                    GraphChiroUnit gewestForGroep = getGraphChiroUnitByLowerUnitName(verbondForGroep.getChildren(), chiroUnit.getGewestNaam());

                    GraphChiroUnit groep = new GraphChiroUnit(chiroUnit.getGroepNaam(), findParticipants(chiroUnit.getGroepStamNummer()), null);
                    gewestForGroep.getChildren().add(groep);
                }
            }
        }
        return root;
    }
/*
    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToMakeSunGraph()")
    public GraphChiroUnit summaryThomas() {
        GraphChiroUnit root = new GraphChiroUnit("Inschrijvingen", null, new ArrayList<GraphChiroUnit>());

        List<RawChiroUnit> allChiroUnits = findAll();

        for (RawChiroUnit unit : allChiroUnits) {
            //System.out.println()unit.getVerbondNaam();
        }


        return root;
    }
*/

    private GraphChiroUnit getGraphChiroUnitByLowerUnitName(List<GraphChiroUnit> units, String unitName) {
        for (GraphChiroUnit unit : units) {
            if (unit.getName().equals(unitName)) {
                return unit;
            }
        }
        return null;
    }

    private Integer findParticipants(String stamNumber) {
        String normalizedStamNumber = stamNumberTrimmer.trim(stamNumber);

        int participants = registrationParticipantService.findParticipantsByGroup(normalizedStamNumber).size();
        int volunteers = registrationParticipantService.findVolunteersByGroup(normalizedStamNumber).size();
        return participants + volunteers;
    }
    /*
    private List<RawChiroUnit> findAll() {
        return chiroUnitRepository.findAll();
    }*/
    //Added by Thomas
    private List<RawChiroUnit> findAllUnitsWithRegisteredParticipants() {
        return chiroUnitRepository.findAllUnitsWithRegisteredParticipants();
    }
/* commented by thomas no longer needed
    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToGetLoginData()")
    public SortedMap getLoginDataFromLastTwoWeeks() {
        return getUniqueLoginsPerVerbond(startDate,endDate);
    }
*/
    @PreAuthorize("@GraphChiroServiceSecurity.hasPermissionToGetLoginData()")
    public LinkedHashMap<Verbond, LinkedHashMap<String, Integer>> getLoginData(Date startDate,Date endDate) throws ParseException {
        return getUniqueLoginsPerVerbond(startDate,endDate);
    }


    public LinkedHashMap<Verbond, LinkedHashMap<String, Integer>> getUniqueLoginsPerVerbond(Date startDate, Date endDate) throws ParseException {
        LinkedHashMap<Verbond, SortedMap<Date, Integer>> uniqueLoginsPerVerbond = new LinkedHashMap<>();
        LinkedHashMap<Verbond,LinkedHashMap<String,Integer>> returnMap =  new LinkedHashMap<>();
        List<LoginLog> allLogs = null;
        List<Date> distinctStamps;

        fillMapWithAllVerbonden(uniqueLoginsPerVerbond);
        DateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");

            allLogs = loginLoggerRepository.findLogsBetweenDates(startDate, endDate);
            distinctStamps = loginLoggerRepository.findDistinctStamps(startDate,endDate);
/*
            distinctStamps = loginLoggerRepository.findDistinctStamps(startDate, endDate)
                    .stream().sorted()
                    .map(date->frmt.format(date))
                    .collect(Collectors.toList());*/
/*
        int y=0;
        for(Date i : distinctStamps)
        {   y++;
            System.out.println(i.toString()+" distinct stamps " + y);
        }
*/
        allLogs.parallelStream().forEach(log -> {
            try {
                mapLoginLogs(log, uniqueLoginsPerVerbond);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        for(LoginLog l: allLogs)
        {
            //System.out.println("all logs print " + frmt.format(l.getStamp()));
        }

        uniqueLoginsPerVerbond.forEach((key, value) -> {
            try {
                uniqueLoginsPerVerbond.put(key, fillMapWithZeroValuesOnDaysWhereThereAreNoLogins(value, distinctStamps));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });


        for(Map.Entry<Verbond, SortedMap<Date, Integer>> entry : uniqueLoginsPerVerbond.entrySet())
        {
            //System.out.println(entry.getValue().size() + " sorted map size");
            LinkedHashMap<String,Integer> conversionMap = new LinkedHashMap<>();
            for(Map.Entry<Date,Integer> e : entry.getValue().entrySet())
            {
            //System.out.println("formated date " +frmt.format(e.getKey()));
            conversionMap.put(frmt.format(e.getKey()),e.getValue());
            //System.out.println("key: "+e.getKey()+" value "+e.getValue());
           // System.out.println("key "+entry.getKey());
           // System.out.println("value "+entry.getValue());
            }
            //System.out.println("size of conversionmap " + conversionMap.size());
            //System.out.println("entry.key " +entry.getKey());
            //System.out.println("conversionmap.values.tostring " + conversionMap.values().toString());
            returnMap.put(entry.getKey(),conversionMap);
        }
        //System.out.println(returnMap.size() + " returnmap size");
        //System.out.println("returnmaps.values.tostring " + returnMap.values().toString());
        return returnMap;
    }

    private void mapLoginLogs(LoginLog log, LinkedHashMap<Verbond, SortedMap<Date, Integer>> treeMap) throws ParseException {
        //System.out.println("call mapLoginLogs");
        Verbond verbondFromStamNumber = Verbond.getVerbondFromStamNumber(log.getStamNumber());
        SortedMap<Date, Integer> dateIntegerSortedMap = fillMapWithDateToLoginCount(log.getStamp(), treeMap.get(verbondFromStamNumber));

        treeMap.put(verbondFromStamNumber, dateIntegerSortedMap);
    }

    private SortedMap<Date, Integer> fillMapWithZeroValuesOnDaysWhereThereAreNoLogins(SortedMap<Date, Integer> sortedMap, List<Date> distinctStamps) throws ParseException {
        //System.out.println("call fillMapWithZeroValesOnDaysWhereThereAreNoLogins");
        SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
        SortedMap<String,Integer> buffer= new TreeMap<>();
        /*for(Map.Entry<Date,Integer> entry : sortedMap.entrySet()) {
            Date key = entry.getKey();
            Integer value = entry.getValue();
            buffer.put(frmt.format(key),value);
            System.out.println(key + " => " + value);
        }*/
        /*
        Iterator<Date> i = distinctStamps.iterator();
        while(i.hasNext())
        {   java.util.Date a = i.next();
            System.out.println(a);
        }*/
        if (sortedMap.size() != distinctStamps.size()) {
            distinctStamps.stream().sorted().forEach(stamp -> {
                if (!sortedMap.containsKey(stamp)) {
                            //System.out.println("stamp = " + stamp);
                    sortedMap.put(stamp, 0);
                }
            });
        }
        /*
         * I know it refers to the same object, but I prefer this way of coding because it looks cleaner to me
         */
        return sortedMap;
    }

    private SortedMap<Date, Integer> fillMapWithDateToLoginCount(java.util.Date date, SortedMap<Date, Integer> map) throws ParseException {
        //System.out.println("call fillMapWithDateToLoginCount");
        SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
        if (map.containsKey(date)) {
            //System.out.println("fillMapWithDateToLoginCount " + frmt.format(date));
            map.put(date, map.get(date) + 1);
        } else {
            //System.out.println("fillMapWithDateToLoginCount " + date);
            map.put(date, 1);
        }
        return map;
    }

    /**
     * Initializes a newly created SortedMap with all Verbonden in it.
     * This method should only be used on empty maps.
     *
     * @param map
     */
    private void fillMapWithAllVerbonden(LinkedHashMap<Verbond, SortedMap<Date, Integer>> map) {
        //System.out.println("call fillMapWithAllVerbonden");
        for (Verbond verbond : Verbond.values()) {
            SortedMap<Date, Integer> uniqueLoginsPerDate = new TreeMap<>();
            map.put(verbond, uniqueLoginsPerDate);
        }
    }
}
