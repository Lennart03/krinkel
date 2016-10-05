package com.realdolmen.chiro.domain_;

import com.realdolmen.chiro.domain.Eatinghabbit;
import com.realdolmen.chiro.domain.Role;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationParticipantTest extends BeanValidatorTest{

    private RegistrationParticipant particpant;

    @Before
    public void setUp(){
        this.particpant = new RegistrationParticipant(
                "123456",
                "Hermione",
                "Granger",
                new Date(),
                "AG0104",
                Gender.WOMAN,
                Role.ASPI,
                Eatinghabbit.VEGI
        );
    }

    @Test
    public void firstNameWithLengthOf2CharactersShouldBeValid(){
        particpant.setFirstName("Jo");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "firstName");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void firstNameWithNormalValueShouldBeValid(){
        particpant.setFirstName("Hermione");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "firstName");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void firstNameShorterThan2CharactersShouldNotBeValid(){
        particpant.setFirstName("H");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "firstName");
        assertEquals(1, violations.size());
    }

    @Test
    public void lastNameWithLengthOf2CharactersShouldBeValid(){
        particpant.setLastName("Me");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "lastName");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void lastNameWithNormalValueShouldBeValid(){
        particpant.setLastName("Granger");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "lastName");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void lastNameShorterThan2CharactersShouldNotBeValid(){
        particpant.setLastName("M");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "lastName");
        assertEquals(1, violations.size());
    }
}
