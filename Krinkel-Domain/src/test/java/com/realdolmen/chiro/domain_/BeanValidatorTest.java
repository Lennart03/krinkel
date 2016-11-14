package com.realdolmen.chiro.domain_;

import org.junit.Before;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Convenience class to test JPA/Hibernate validation annotations.
 *
 * Usage: Extend this class and call the {@link #validator()} method
 * to get the Validator instance configured by this class.
 *
 * Example test code:
 * <pre>
 * {@code
 *
 *      // Create a new instance of some object
 *      Address address = new Address("Suikerhoek", "7A", 3221, "Nieuwrode");
 *
 *      // Pass the new instance to the validator
 *      // Make sure to adapt the generic of the ConstraintViolation to the specific class.
 *      Set<ConstraintViolation<Address>> violations = validator().validate(address);
 *
 *      // Assert that there are no errors.
 *      Assert.assertTrue(violations.isEmpty());
 *
 *      // Alternatively with non valid input, check that one or
 *      // more validation errors are found.
 *      Assert.assertEquals(1, violations.size());
 *
 *      // Alternative call to validator:
 *      // Check only for errors on certain property.
 *      validator().validateProperty(address, "street");
 * }
 * </pre>
 */
public abstract class BeanValidatorTest {

    private Validator validator;

    @Before
    public void beanValidatorSetUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    protected Validator validator() {
        return validator;
    }
}
