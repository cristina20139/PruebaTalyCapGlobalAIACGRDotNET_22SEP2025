package com.clinicos.backend.api.rest.infrastructure.controller;

import com.clinicos.backend.api.rest.application.ClienteService;
import com.clinicos.backend.api.rest.domain.model.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * ✅ Manejo de Códigos HTTP implementado en este controlador:
 *
 * - 200 OK:
 *   Se devuelve cuando el cliente existe en la base de datos.
 *   Ejemplo en el código:
 *       return ResponseEntity.ok(cliente);
 *
 * - 400 Bad Request:
 *   Se devuelve cuando los parámetros son inválidos (ej. tipoDocumento vacío o numeroDocumento <= 0).
 *   Implementación:
 *       throw new IllegalArgumentException("mensaje...");
 *   Capturado por GlobalExceptionHandler -> ResponseEntity.badRequest().
 *
 * - 404 Not Found:
 *   Se devuelve cuando no se encuentra un cliente con los datos proporcionados.
 *   Implementación:
 *       .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));
 *   Capturado por GlobalExceptionHandler -> ResponseEntity.status(HttpStatus.NOT_FOUND).
 *
 * - 500 Internal Server Error:
 *   Se devuelve cuando ocurre una excepción inesperada en la aplicación.
 *   Implementación:
 *       @ExceptionHandler(RuntimeException.class) en GlobalExceptionHandler
 *       devuelve ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
 *
 * 🔎 Con este diseño, el controlador asegura el cumplimiento del requerimiento:
 *   "Es indispensable el manejo de códigos HTTP en la respuesta (200, 400, 404 y 500)".
 */

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operaciones sobre clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Obtener un cliente por tipo y número de documento.
     *
     * @param tipoDocumento tipo de documento (CC, CE, etc.)
     * @param numeroDocumento número del documento
     * @return 200 OK si se encuentra, 404 Not Found si no existe, 400 si los parámetros no son válidos
     */
    @Operation(summary = "Obtener un cliente por tipo y número de documento")
    @GetMapping("/{tipoDocumento}/{numeroDocumento}")
    public ResponseEntity<Cliente> obtenerCliente(
            @PathVariable String tipoDocumento,
            @PathVariable long numeroDocumento
    ) {
        logger.info("🔍 Buscando cliente con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento);

        if (tipoDocumento == null || tipoDocumento.isBlank()) {
            throw new IllegalArgumentException("El tipo de documento no puede estar vacío");
        }
        if (numeroDocumento <= 0) {
            throw new IllegalArgumentException("El número de documento debe ser mayor que 0");
        }

        return clienteService.obtenerCliente(tipoDocumento, numeroDocumento)
                .map(cliente -> {
                    logger.info("✅ Cliente encontrado: {}", cliente);
                    return ResponseEntity.ok(cliente); // 200 OK
                })
                .orElseThrow(() -> {
                    logger.warn("❌ Cliente no encontrado con tipoDocumento={} y numeroDocumento={}",
                            tipoDocumento, numeroDocumento);
                    return new NoSuchElementException("Cliente no encontrado");
                });
    }
}
