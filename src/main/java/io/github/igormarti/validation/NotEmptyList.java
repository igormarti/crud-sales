package io.github.igormarti.validation;

import io.github.igormarti.validation.constraintsvalidation.NotEmptyListValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidation.class)
public @interface NotEmptyList {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String message() default "A lista não pode ser vazia.";
}
