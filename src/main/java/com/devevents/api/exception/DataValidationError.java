package com.devevents.api.exception;

import org.springframework.validation.FieldError;

public record DataValidationError(String field, String message) {
    public DataValidationError(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}