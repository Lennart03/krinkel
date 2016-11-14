package com.realdolmen.chiro.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StamNumberTrimmerTest {

    private StamNumberTrimmer trimmer;

    @Before
    public void setUp(){
         trimmer = new StamNumberTrimmer();
    }

    @Test
    public void trimGivesCorrectResult(){
        String originalStamNumber = "AG /0103";
        assertEquals("AG0103", trimmer.trim(originalStamNumber));
    }

    @Test
    public void trimWithEmptyStringGivesEmptyString(){
        assertEquals("", trimmer.trim(""));
    }

    @Test
    public void trimWithNullGivesEmptyString(){
        assertEquals("", trimmer.trim(null));
    }
}
