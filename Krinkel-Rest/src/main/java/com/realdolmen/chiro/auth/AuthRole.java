package com.realdolmen.chiro.auth;

import com.realdolmen.chiro.domain.EventRole;
import com.realdolmen.chiro.domain.SecurityRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Example:
 *
 * @AuthRole(role = { SecurityRole.ADMIN })
 * public void someMethod(){}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthRole {
    SecurityRole[] roles();
}

