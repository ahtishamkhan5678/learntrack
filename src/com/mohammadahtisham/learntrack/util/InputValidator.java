package com.mohammadahtisham.learntrack.util;

import com.mohammadahtisham.learntrack.exception.InvalidInputException;

public class InputValidator {
    private InputValidator() {

    }

    public static void requireNonEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty");
        }
    }

    public static void validateEmail(String email) throws InvalidInputException {
        if (!email.contains("@")) {
            throw new InvalidInputException("Invalid email: " + email);
        }
    }
}
