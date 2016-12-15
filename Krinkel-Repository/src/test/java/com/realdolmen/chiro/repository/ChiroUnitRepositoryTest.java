package com.realdolmen.chiro.repository;

import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.domain.units.RawChiroUnit;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by JCPBB69 on 15/12/2016.
 */
public class ChiroUnitRepositoryTest extends SpringIntegrationTest{
    @Autowired
    private ChiroUnitRepository chiroUnitRepository;

    @Test
    public void findAllGewesten(){
        List<ChiroUnit> allGewesten = chiroUnitRepository.findAllGewesten();
        assertEquals(72,allGewesten.size());
    }

    @Test
    public void findAllGroups(){
        List<RawChiroUnit> allGroups = chiroUnitRepository.findAll();
        assertEquals(904,allGroups.size());
    }

    @Test
    public void testCountVolunteerFromVerbond(){
        int count = chiroUnitRepository.countParticipantsByVerbond("AG /0000");
        assertEquals(4, count);
    }

    @Test
    public void testCountParticipantFromVerbond(){
        int count = chiroUnitRepository.countVolunteersByVerbond("AG /0000");
        assertEquals(1, count);
    }

}
