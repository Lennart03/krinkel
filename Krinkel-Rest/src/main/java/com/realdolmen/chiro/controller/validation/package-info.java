/**
 * Purpose: Wrap errors in a friendly JSON format.
 * Makes the backend / frontend less technology agnostic
 * (avoids Spring Exception types mentioned in reponses)
 * and thus increases portability. :)
 *
 * Classes in this package are adapted from
 * http://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/
 * and
 * https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-adding-validation-to-a-rest-api/
 *
 * To enable the error handling add the @EnableRestErrorHandling at the top of your RestController.
 * Also make you use either the @Valid or @Validated annotations on the @RequestBody.
 */
package com.realdolmen.chiro.controller.validation;