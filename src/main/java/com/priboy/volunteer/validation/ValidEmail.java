package com.priboy.volunteer.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidEmailImpl.class)
@Documented
public @interface ValidEmail {
    String message() default "Неверный формат электронный почты";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
