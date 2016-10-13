package com.realdolmen.chiro.controller.validation;

import java.lang.annotation.*;

/**
 * Enables fancy formatting of JSR-303 validation (and other) errors.
 * Should only be set on @RestController's.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableRestErrorHandling {
}
