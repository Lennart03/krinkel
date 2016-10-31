package com.realdolmen.chiro.domain.mothers;

import com.realdolmen.chiro.domain.*;

import java.util.Calendar;

/**
 * Object Mother used for testing.
 */
public class RegistrationParticipantMother {

    private RegistrationParticipantMother(){
        // Hide constructor.
    }

    public static RegistrationParticipant createBasicRegistrationParticipant(){
        Calendar c = Calendar.getInstance();
        c.set(1979, Calendar.SEPTEMBER, 19);

        RegistrationParticipant.RegistrationParticipantBuilder builder =
                new RegistrationParticipant.RegistrationParticipantBuilder();

        builder.adNumber("778899")
                .email("hermione@hogwarts.example")
                .emailSubscriber("aster.deckers@example.org")
                .firstName("Hermione")
                .lastName("Granger")
                .birthdate(c.getTime())
                .stamnumber("AG0104")
                .gender(Gender.WOMAN)
                .eatinghabbit(Eatinghabbit.VEGI)
                .role(EventRole.ASPI)
                .address(
                        new Address("Little Whinging street","32", 1380, "Waterloo")
                );

        return builder.build();
    }
}
