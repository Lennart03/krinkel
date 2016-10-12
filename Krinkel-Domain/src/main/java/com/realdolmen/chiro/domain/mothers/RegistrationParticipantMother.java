package com.realdolmen.chiro.domain.mothers;

import com.realdolmen.chiro.domain.*;

import java.util.Calendar;

/**
 * Object Mother used for testing.
 */
public class RegistrationParticipantMother {
    public static RegistrationParticipant createBasicRegistrationParticipant(){
        Calendar c = Calendar.getInstance();
        c.set(1979, Calendar.SEPTEMBER, 19);

        RegistrationParticipant.RegistrationParticipantBuilder builder =
                new RegistrationParticipant.RegistrationParticipantBuilder();

        builder.adNumber("123456")
                .email("hermione@hogwarts.example")
                .firstName("Hermione")
                .lastName("Granger")
                .birthdate(c.getTime())
                .stamnumber("AG0104")
                .gender(Gender.WOMAN)
                .eatinghabbit(Eatinghabbit.VEGI)
                .role(Role.ASPI)
                .address(
                        new Address("Little Whinging street","32", 1380, "Waterloo")
                );

        return builder.build();
    }
}
