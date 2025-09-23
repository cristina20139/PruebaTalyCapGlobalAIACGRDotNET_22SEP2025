package com.clinicos.backend.api.rest.infrastructure.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        logger.warn("❌ Error de validación: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body("❌ Error de validación: " + ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        logger.info("❌ Recurso no encontrado", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("❌ Recurso no encontrado");
    }

    /**
     * Maneja cualquier RuntimeException que no haya sido capturada por otros handlers.
     * Swagger y los endpoints de documentación no se ven afectados.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        logger.error("⚠️ Error inesperado en la aplicación", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("⚠️ Error interno en el servidor: " + ex.getMessage());
    }
}
