package com.realdolmen.chiro.config;

import com.realdolmen.chiro.domain.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Example:
 * @AuthRole(role = { Role.ADMIN })
 * public void someMethod(){}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthRole {
    Role[] roles();
}

