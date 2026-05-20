package com.todoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTaskNotFound(TaskNotFoundException ex) {
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", "Les données envoyées sont invalides.");

        Map<String, String> champsErreurs = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> champsErreurs.put(e.getField(), e.getDefaultMessage()));
        reponse.put("details", champsErreurs);

        return ResponseEntity.badRequest().body(reponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(reponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", "Valeur invalide pour le paramètre '" + ex.getName() + "' : " + ex.getValue());
        return ResponseEntity.badRequest().body(reponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", "Une erreur inattendue s'est produite. Veuillez réessayer.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reponse);
    }
}
