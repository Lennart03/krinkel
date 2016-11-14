package com.realdolmen.chiro.domain_;

import com.realdolmen.chiro.domain.VolunteerFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VolunteerFunctionTest {

    @Test
    public void volunteerFunctionWithOtherPropertySetsPresetToCustom(){
        VolunteerFunction volunteerFunction = new VolunteerFunction("Dolphin Entertainer");
        assertEquals(VolunteerFunction.Preset.CUSTOM, volunteerFunction.getPreset());
        assertEquals("Dolphin Entertainer", volunteerFunction.getOther());
    }
}
