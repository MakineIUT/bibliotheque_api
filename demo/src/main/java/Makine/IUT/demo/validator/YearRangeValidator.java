package Makine.IUT.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearRangeValidator implements ConstraintValidator<YearRange, Integer> {

    private static final int MIN_YEAR = 1450;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        int currentYear = Year.now().getValue();
        return value >= MIN_YEAR && value <= currentYear;
    }
}
