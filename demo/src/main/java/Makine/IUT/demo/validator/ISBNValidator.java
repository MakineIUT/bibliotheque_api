package Makine.IUT.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        // Remove hyphens and spaces for validation
        String cleanedIsbn = value.replaceAll("[-\\s]", "");

        // Check if it's ISBN-10 or ISBN-13
        return isValidISBN10(cleanedIsbn) || isValidISBN13(cleanedIsbn);
    }

    private boolean isValidISBN10(String isbn) {
        if (isbn.length() != 10) {
            return false;
        }

        // ISBN-10 format: 9 digits + check digit (0-9 or X)
        return isbn.matches("\\d{9}[0-9X]");
    }

    private boolean isValidISBN13(String isbn) {
        if (isbn.length() != 13) {
            return false;
        }

        // ISBN-13 format: 13 digits
        return isbn.matches("\\d{13}");
    }
}
