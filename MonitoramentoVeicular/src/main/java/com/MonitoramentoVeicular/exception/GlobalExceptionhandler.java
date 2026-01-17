package com.MonitoramentoVeicular.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionhandler {

    // Validação DTO (@NotBlank, @Email, etc)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> erros.put(
                        err.getField(),
                        err.getDefaultMessage()
                ));

        return ResponseEntity.badRequest().body(erros);
    }

    // Regras de negócio (IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBusiness(
            IllegalArgumentException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}