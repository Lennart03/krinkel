package com.realdolmen.chiro.domain_;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.mothers.RegistrationParticipantMother;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Calendar;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class RegistrationParticipantTest extends BeanValidatorTest{

    private RegistrationParticipant participant;

    @Before
    public void setUp(){
        this.participant = RegistrationParticipantMother.createBasicRegistrationParticipant();
    }

    @Test
    public void firstNameWithLengthOf2CharactersShouldBeValid(){
        participant.setFirstName("Jo");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "firstName");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void firstNameWithNormalValueShouldBeValid(){
        participant.setFirstName("Hermione");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "firstName");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void firstNameShorterThan2CharactersShouldNotBeValid(){
        participant.setFirstName("H");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "firstName");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void lastNameWithLengthOf2CharactersShouldBeValid(){
        participant.setLastName("Me");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "lastName");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void lastNameWithNormalValueShouldBeValid(){
        participant.setLastName("Granger");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "lastName");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void lastNameShorterThan2CharactersShouldNotBeValid(){
        participant.setLastName("M");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "lastName");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void emailAddressEmptyShouldBeInvalid(){
        participant.setEmail("");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "email");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void emailAddressNullShouldBeInvalid(){
        participant.setEmail(null);
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "email");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void validPhoneNumberShouldBeValid(){
        participant.setPhoneNumber("016539999");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void phoneNumberWithSpacesShouldBeValid(){
        participant.setPhoneNumber("016 53 99 99");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void phoneNumberWithCountryCodeShouldBeValid(){
        participant.setPhoneNumber("+3216539999");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void mobilePhoneAsPhoneNumberWithCountryCodeShouldBeValid(){
        Set<ConstraintViolation<RegistrationParticipant>> violations;

        participant.setPhoneNumber("+32486987654");
        violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());

        participant.setPhoneNumber("+32488336677");
        violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());

        participant.setPhoneNumber("+32460336677");
        violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());

        participant.setPhoneNumber("+32473446677");
        violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());

        participant.setPhoneNumber("+32493446677");
        violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());

        participant.setPhoneNumber("+32453446677");
        violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void mobilePhoneAsPhoneNumberShouldBeValid(){
        participant.setPhoneNumber("04869876");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void incorrectPhoneNumberShouldBeInvalid(){
        participant.setPhoneNumber("-5sdfmoklsfjkljia");
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "phoneNumber");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void birthDateInPastShouldBeValid(){
        Calendar c = Calendar.getInstance();
        c.set(1999, Calendar.APRIL, 1);

        participant.setBirthdate(c.getTime());
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "birthdate");
        assertTrue(violations.isEmpty());
    }

    @Test
    public void birthDateInFutureShouldBeInvalid(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 10); // I have travelled some 10 years into the future.

        participant.setBirthdate(c.getTime());
        Set<ConstraintViolation<RegistrationParticipant>> violations = validator().validateProperty(participant, "birthdate");
        Assert.assertEquals(1, violations.size());
    }
}
