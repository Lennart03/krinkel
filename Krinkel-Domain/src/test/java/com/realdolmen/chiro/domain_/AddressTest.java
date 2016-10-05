package com.realdolmen.chiro.domain_;

import com.realdolmen.chiro.domain.Address;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AddressTest extends BeanValidatorTest{

    //<editor-fold desc="Green Path"/>
    @Test
    public void normalAddressShouldBeValid(){
        Address address = new Address("Tervuursevest", "101", 3001, "Heverlee");
        Set<ConstraintViolation<Address>> violations = validator().validate(address);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void addressWithAlphaNumericHouseNumberShouldBeValid(){
        Address address = new Address("Suikerhoek", "7A", 3221, "Nieuwrode");
        Set<ConstraintViolation<Address>> violations = validator().validate(address);
        Assert.assertTrue(violations.isEmpty());
    }
    //</editor-fold>

    //<editor-fold desc="Invalid input"/>

    /*   Street:
     *       [v] valid
     *       [v] NULL
     *       [v] Empty string
     *       [v] Long but not too long
     *       [] Length is too long
     *   House Number:
     *       [v] valid
     *       [v] NULL
     *       [v] Empty string
     *       [] Too Long
     *
     *   City:
     *       [v] valid
     *       [v] NULL
     *       [v] Empty string
     *       [v] Long but not too long
     *       [] Too Long
     *
     *   Postal code:
     *       [v] valid (3000)
     *       [v] Negative number
     *       [v] Zero
     *       [v] Equal to 999
     *       [v] Equal to 1000 (Brussel)
     *       [v] Equal to 9999
     *       [v] Equal to 10000
     *
     */
    @Test
    public void addressWithEmptyStreetNameShouldBeInvalid(){
        Address address = new Address("","101", 3001, "Heverlee");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "street");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void addressWithNullStreetNameShouldBeInvalid(){
        Address address = new Address(null,"101", 3001, "Heverlee");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "street");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void addressWithLongButExistingStreetNameShouldBeValid(){
        Address address = new Address("Burgemeester Charles Rotsart de Hertainglaan","1", 9990, "Maldegem"); // 44 characters
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "street");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void addressWithEmptyCityShouldBeInvalid(){
        Address address = new Address("Tervuursevest","101", 3001, "");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "city");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void addressWithNullCityShouldBeInvalid(){
        Address address = new Address("Tervuursevest","101", 3001, null);
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "city");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void addressWithAnExistingLongCityNameShouldBeValid(){
        Address address = new Address("De lange bomen dreef","42", 5000, "Nil-Saint-Vincent-Saint-Martin");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "city");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void addressWithNegativePostalCodeShouldBeInvalid(){
        Address address = new Address("The very pessimistic street","101", -9000, "Townsville");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "postalCode");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void addressWithPostalCodeZeroShouldBeInvalid(){
        Address address = new Address("The empty street","101", 0, "Townsville");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "postalCode");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void addressWithPostalCode999ShouldBeInvalid(){
        Address address = new Address("Brusselsesteenweg","101", 999, "Not quite Brussel");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "postalCode");
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void addressWithPostalCode1000ShouldBeValid(){
        Address address = new Address("Brusselsestraat","101", 1000, "Brussel");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "postalCode");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void addressWithPostalCode9999ShouldBeValid(){
        Address address = new Address("End of the road","999", 9999, "Ender city");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "postalCode");
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void addressWithPostalCode10000ShouldBeInvalid(){
        Address address = new Address("Milleniumstreet","10101", 10000, "Scope Town");
        Set<ConstraintViolation<Address>> violations = validator().validateProperty(address, "postalCode");
        Assert.assertEquals(1, violations.size());
    }
    //</editor-fold>
}
