package Makine.IUT.demo.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ISBNValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBN {
    String message() default "ISBN format is invalid (must be ISBN-10 or ISBN-13)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
