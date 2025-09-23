package infrastructure.controller;

import com.clinicos.backend.api.rest.infrastructure.controller.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleBadRequest_ShouldReturn400() {
        IllegalArgumentException ex = new IllegalArgumentException("Parámetro inválido");

        ResponseEntity<String> response = exceptionHandler.handleBadRequest(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("❌ Error de validación: Parámetro inválido"));
    }

    @Test
    void handleNotFound_ShouldReturn404() {
        NoSuchElementException ex = new NoSuchElementException("Cliente no encontrado");

        ResponseEntity<String> response = exceptionHandler.handleNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("❌ Recurso no encontrado"));
    }

    @Test
    void handleRuntime_ShouldReturn500() {
        RuntimeException ex = new RuntimeException("Falla inesperada");

        ResponseEntity<String> response = exceptionHandler.handleRuntime(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("⚠️ Error interno en el servidor: Falla inesperada"));
    }
}
