package com.example.lab2.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

// Кастомний виняток для помилок валідації
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyValidationException extends RuntimeException {
    private final Map<String, String> validationErrors;

    public MyValidationException(Map<String, String> validationErrors) {
        super("Validation failed");
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
}
