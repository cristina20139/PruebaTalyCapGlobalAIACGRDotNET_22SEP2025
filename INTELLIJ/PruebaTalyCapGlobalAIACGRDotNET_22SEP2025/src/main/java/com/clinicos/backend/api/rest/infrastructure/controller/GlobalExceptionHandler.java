package com.clinicos.backend.api.rest.infrastructure.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * Manejador global de excepciones para la aplicación.
 * <p>
 * Esta clase centraliza el manejo de errores de todos los controladores REST,
 * siguiendo el patrón de <b>RestControllerAdvice</b> en Spring Boot.
 * Permite retornar códigos HTTP consistentes y mensajes claros,
 * manteniendo la lógica de manejo de errores separada de los controladores.
 * <p>
 * Se aplican principios de <b>Clean Code</b> y <b>SOLID</b>:
 * <ul>
 *   <li><b>Single Responsibility Principle:</b> esta clase solo maneja excepciones.</li>
 *   <li><b>Open/Closed Principle:</b> se pueden agregar nuevos handlers sin modificar los existentes.</li>
 *   <li><b>Dependency Inversion Principle:</b> los controladores dependen de este manejo global de errores
 *       en lugar de implementar lógica de captura de excepciones repetitiva.</li>
 * </ul>
 * <p>
 * Se recomienda el uso de logs para registrar trazabilidad, advertencias y errores inesperados.
 *
 * @author Aura
 * Cristina Garzon Rodriguez
 * @since 23 Sep 2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja errores de validación o parámetros inválidos.
     * <p>
     * Devuelve un código HTTP 400 (Bad Request) y un mensaje descriptivo.
     * Registra un log de advertencia con el detalle de la excepción.
     *
     * @param ex Excepción de tipo {@link IllegalArgumentException}
     * @return {@link ResponseEntity} con mensaje de error y HTTP 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        logger.warn("❌ Error de validación: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body("❌ Error de validación: " + ex.getMessage());
    }

    /**
     * Maneja casos donde el recurso no es encontrado.
     * <p>
     * Devuelve un código HTTP 404 (Not Found) y un mensaje genérico.
     * Registra un log informativo con la excepción.
     *
     * @param ex Excepción de tipo {@link NoSuchElementException}
     * @return {@link ResponseEntity} con mensaje de error y HTTP 404
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        logger.info("❌ Recurso no encontrado", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("❌ Recurso no encontrado");
    }

    /**
     * Maneja cualquier {@link RuntimeException} no capturada por otros handlers.
     * <p>
     * Devuelve un código HTTP 500 (Internal Server Error) y el mensaje de la excepción.
     * Permite centralizar errores inesperados y mantener los endpoints limpios.
     * Registra un log de error completo con stacktrace.
     *
     * @param ex Excepción de tipo {@link RuntimeException}
     * @return {@link ResponseEntity} con mensaje de error y HTTP 500
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        logger.error("⚠️ Error inesperado en la aplicación", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("⚠️ Error interno en el servidor: " + ex.getMessage());
    }
}
