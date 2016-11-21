package com.realdolmen.chiro.domain.mothers;

import com.realdolmen.chiro.domain.*;

import java.util.Calendar;

/**
 * Object Mother used for testing.
 */
public class RegistrationVolunteerMother {

    public static RegistrationVolunteer createBasicRegistrationVolunteer() {
        Calendar c = Calendar.getInstance();
        c.set(1995, Calendar.AUGUST, 5);

        RegistrationVolunteer volunteer = new RegistrationVolunteer(
                "386283", "aster.deckers@example.org", "Aster", "Deckers", c.getTime(),
                "AG0001", Gender.MAN, EventRole.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KRINKEL_EDITORIAL), "aster.deckers@example.org"
        );
        volunteer.setAddress(new Address("-", "-", 1500, "-"));

        return volunteer;
    }

    public static RegistrationVolunteer createRegistrationVolunteerWithManyPreCamps() {
        RegistrationVolunteer volunteer = RegistrationVolunteerMother.createBasicRegistrationVolunteer();


        Calendar c = Calendar.getInstance();
        c.set(2017, Calendar.AUGUST, 21, 0, 0, 0);

        PreCamp pre = new PreCamp();

        pre.setId(10);
        pre.setDate(c.getTime());

        volunteer.addPreCamp(pre);

        c.set(2017, Calendar.AUGUST, 22, 0, 0, 0);

        pre = new PreCamp();
        pre.setId(20);
        pre.setDate(c.getTime());

        volunteer.addPreCamp(pre);

        return volunteer;
    }
}
