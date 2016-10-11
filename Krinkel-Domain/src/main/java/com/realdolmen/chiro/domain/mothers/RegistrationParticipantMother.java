package com.realdolmen.chiro.domain.mothers;

import com.realdolmen.chiro.domain.Eatinghabbit;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.Role;

import java.util.Calendar;

/**
 * Object Mother used for testing.
 */
public class RegistrationParticipantMother {
    public static RegistrationParticipant createBasicRegistrationParticipant(){
        Calendar c = Calendar.getInstance();
        c.set(1979, Calendar.SEPTEMBER, 19);

        return new RegistrationParticipant(
                "123456",
                "hermione@hogwarts.example",
                "Hermione",
                "Granger",
                c.getTime(),
                "AG0104",
                Gender.WOMAN,
                Role.ASPI,
                Eatinghabbit.VEGI
        );
    }
}
