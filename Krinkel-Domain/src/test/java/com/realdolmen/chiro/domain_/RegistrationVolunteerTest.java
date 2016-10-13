package com.realdolmen.chiro.domain_;

import com.realdolmen.chiro.domain.*;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class RegistrationVolunteerTest extends BeanValidatorTest{

    @Test
    public void constructorWorksAsExpectedAndCreatesAValidInstance(){
        RegistrationVolunteer volunteer = new RegistrationVolunteer(
                "123456789", "aster.deckers@example.com", "Aster", "Deckers", new Date(),
                "AG0001", Gender.MAN, Role.LEADER, Eatinghabbit.VEGI,
                CampGround.ANTWERPEN,
                new VolunteerFunction(VolunteerFunction.Preset.KRINKEL_EDITORIAL)
        );
        volunteer.setAddress(
                new Address("-", "-", 1500, "-")
        );

        Set<ConstraintViolation<RegistrationVolunteer>> violations = validator().validate(volunteer);
        Assert.assertTrue(violations.isEmpty());
    }
}
