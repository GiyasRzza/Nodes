package com.example.nodess.LocalAnatation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ProfileImageValidator.class})
public @interface ProfileImage {
    String message() default "{message.image}";
    Class<?>[] groups() default { };
    Class<?extends Payload>[] payload() default { };
    String[] types ();
}
