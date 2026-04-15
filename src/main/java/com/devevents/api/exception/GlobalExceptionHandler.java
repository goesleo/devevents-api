package com.devevents.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice // Diz ao Spring: "Fique de olho em todos os Controllers. Se der erro, mande pra cá."
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DataValidationError>> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<DataValidationError> errors = ex.getFieldErrors()
                .stream()
                .map(DataValidationError::new)
                .toList();

        return ResponseEntity.badRequest().body(errors); // Retorna 400 Bad Request
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBusinessRules(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericErrors(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro interno no servidor. Tente novamente mais tarde.");
    }
}