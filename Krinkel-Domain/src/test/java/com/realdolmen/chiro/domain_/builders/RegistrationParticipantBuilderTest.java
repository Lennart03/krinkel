package com.realdolmen.chiro.domain_.builders;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegistrationParticipantBuilderTest {

    @Test
    public void builderMakesNewInstanceWithCorrectValues(){
        RegistrationParticipant.RegistrationParticipantBuilder builder
                = new RegistrationParticipant.RegistrationParticipantBuilder();

        RegistrationParticipant build = builder.email("hermione@hogwarts.org")
                .adNumber("123456")
                .stamnumber("AG1000")
                .build();

        assertEquals("hermione@hogwarts.org", build.getEmail());
        assertEquals("123456", build.getAdNumber());
        assertEquals("AG1000", build.getStamnumber());
    }
}
