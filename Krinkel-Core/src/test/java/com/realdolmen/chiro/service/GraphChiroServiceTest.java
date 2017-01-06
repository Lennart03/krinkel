package com.realdolmen.chiro.service.security;

import com.realdolmen.chiro.domain.Verbond;
import com.realdolmen.chiro.service.GraphChiroService;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by HBSBB70 on 20/12/2016.
 */
@ContextConfiguration(classes={com.realdolmen.chiro.config.TestConfig.class})
public class GraphChiroServiceTest extends SpringIntegrationTest {

    @Autowired
    GraphChiroService graphChiroService;


    @Test
    public void getUniqueLoginsPerVerbondWithinBoundsTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date start = simpleDateFormat.parse("01/12/2016");
        Date end = simpleDateFormat.parse("31/12/2016");
        LinkedHashMap<Verbond, LinkedHashMap<String, Integer>> uniqueLoginsPerVerbond = graphChiroService.getUniqueLoginsPerVerbond(start, end);
        for (Map.Entry<Verbond, LinkedHashMap<String, Integer>> verbondSortedMapEntry : uniqueLoginsPerVerbond.entrySet()) {
            System.err.println("VERBOND: " + verbondSortedMapEntry.getKey());
            int counter = 1;
            for (Map.Entry<String, Integer> stringIntegerEntry : verbondSortedMapEntry.getValue().entrySet()) {
                System.err.println(counter + ") Key: " + stringIntegerEntry.getKey() + " Value: " + stringIntegerEntry.getValue());
                counter++;
            }
        }
    }
}
