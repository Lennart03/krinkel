package com.realdolmen.chiro.domain_;

import org.junit.Before;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public abstract class BeanValidatorTest {

    private Validator validator;

    @Before
    public void beanValidatorSetUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public Validator validator() {
        return validator;
    }
}
