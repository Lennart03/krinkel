package com.realdolmen.chiro.domain_;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.mothers.RegistrationParticipantMother;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationParticipantTest extends BeanValidatorTest{

    private RegistrationParticipant particpant;

    @Before
    public void setUp(){
        this.particpant = RegistrationParticipantMother.createBasicRegistrationParticipant();
    }

    @Test
    public void firstNameWithLengthOf2CharactersShouldBeValid(){
        particpant.setFirstName("Jo");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "firstName");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void firstNameWithNormalValueShouldBeValid(){
        particpant.setFirstName("Hermione");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "firstName");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void firstNameShorterThan2CharactersShouldNotBeValid(){
        particpant.setFirstName("H");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "firstName");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void lastNameWithLengthOf2CharactersShouldBeValid(){
        particpant.setLastName("Me");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "lastName");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void lastNameWithNormalValueShouldBeValid(){
        particpant.setLastName("Granger");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "lastName");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void lastNameShorterThan2CharactersShouldNotBeValid(){
        particpant.setLastName("M");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "lastName");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void emailAddressEmptyShouldBeInvalid(){
        particpant.setEmail("");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "email");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void emailAddressNullShouldBeInvalid(){
        particpant.setEmail(null);
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "email");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void validPhoneNumberShouldBeValid(){
        particpant.setPhoneNumber("016539999");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void phoneNumberWithSpacesShouldBeValid(){
        particpant.setPhoneNumber("016 53 99 99");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void phoneNumberWithCountryCodeShouldBeValid(){
        particpant.setPhoneNumber("+3216539999");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void mobilePhoneAsPhoneNumberWithCountryCodeShouldBeValid(){
        particpant.setPhoneNumber("+32486987654");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void mobilePhoneAsPhoneNumberShouldBeValid(){
        particpant.setPhoneNumber("0486987654");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void incorrectPhoneNumberShouldBeValid(){
        particpant.setPhoneNumber("-5sdfmoklsfjkljia");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(particpant, "phoneNumber");
        Assert.assertEquals(1, violations.size());
    }
}
