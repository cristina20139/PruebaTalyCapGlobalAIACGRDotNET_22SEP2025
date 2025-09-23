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
 * Controlador REST que expone operaciones sobre {@link Cliente}.
 * <p>
 * Esta clase pertenece a la capa de infraestructura y actúa como adaptador de entrada
 * en la <b>arquitectura hexagonal</b>. Su responsabilidad principal es recibir solicitudes
 * HTTP relacionadas con clientes y delegar la lógica de negocio a {@link ClienteService}.
 * <p>
 * Se aplican principios de <b>Clean Code</b> y <b>SOLID</b>:
 * <ul>
 *   <li><b>Single Responsibility Principle:</b> el controlador solo se encarga de exponer
 *       endpoints y delegar la lógica.</li>
 *   <li><b>Dependency Inversion Principle:</b> depende de la abstracción ClienteService,
 *       desacoplando la capa de aplicación de la infraestructura.</li>
 *   <li><b>Open/Closed Principle:</b> se pueden agregar nuevos endpoints sin modificar
 *       la lógica existente.</li>
 *   <li><b>Logs claros y consistentes:</b> se registran operaciones de información,
 *       advertencia y error para facilitar trazabilidad y depuración.</li>
 * </ul>
 * <p>
 * Los errores se manejan mediante {@link org.springframework.web.bind.annotation.RestControllerAdvice},
 * siguiendo el patrón de manejo global de excepciones.
 *
 * @author Aura
 * Cristina Garzon Rodriguez
 * @since 23 Sep 2025
 */
@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operaciones sobre clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    /**
     * Constructor que inyecta {@link ClienteService}.
     * <p>
     * Se sigue el principio de <b>Dependency Injection</b>, desacoplando el controlador
     * de la implementación concreta del servicio.
     *
     * @param clienteService Servicio de aplicación para manejar la lógica de negocio de clientes.
     */
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Obtiene un cliente por tipo y número de documento.
     * <p>
     * Este método:
     * <ul>
     *   <li>Valida los parámetros de entrada.</li>
     *   <li>Registra logs de inicio, advertencias y errores.</li>
     *   <li>Devuelve códigos HTTP adecuados:</li>
     *   <ul>
     *       <li>200 OK: cliente encontrado</li>
     *       <li>400 Bad Request: parámetros inválidos</li>
     *       <li>404 Not Found: cliente no encontrado</li>
     *       <li>500 Internal Server Error: error inesperado</li>
     *   </ul>
     *   <li>Se adhiere al principio de <b>Single Responsibility</b> y mantiene el controlador
     *       limpio delegando la lógica a {@link ClienteService}.</li>
     * </ul>
     *
     * @param tipoDocumento Tipo de documento del cliente (CC, CE, Pasaporte, etc.). No puede ser {@code null} ni vacío.
     * @param numeroDocumento Número de documento, mayor que 0.
     * @return {@link ResponseEntity} con el cliente encontrado o error correspondiente.
     * @throws IllegalArgumentException Si los parámetros son inválidos (HTTP 400).
     * @throws NoSuchElementException Si el cliente no se encuentra (HTTP 404).
     * @throws RuntimeException Para errores inesperados (HTTP 500).
     */
    @Operation(summary = "Obtener un cliente por tipo y número de documento")
    @GetMapping("/{tipoDocumento}/{numeroDocumento}")
    public ResponseEntity<Cliente> obtenerCliente(
            @PathVariable String tipoDocumento,
            @PathVariable long numeroDocumento
    ) {
        logger.info("🔍 Iniciando búsqueda de cliente con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento);

        // Validación de parámetros
        if (tipoDocumento == null || tipoDocumento.isBlank()) {
            logger.warn("⚠️ Parámetro tipoDocumento inválido: '{}'", tipoDocumento);
            throw new IllegalArgumentException("El tipo de documento no puede estar vacío");
        }
        if (numeroDocumento <= 0) {
            logger.warn("⚠️ Parámetro numeroDocumento inválido: {}", numeroDocumento);
            throw new IllegalArgumentException("El número de documento debe ser mayor que 0");
        }

        try {
            return clienteService.obtenerCliente(tipoDocumento, numeroDocumento)
                    .map(cliente -> {
                        logger.info("✅ Cliente encontrado: {}", cliente);
                        return ResponseEntity.ok(cliente); // 200 OK
                    })
                    .orElseThrow(() -> {
                        logger.warn("❌ Cliente no encontrado con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento);
                        return new NoSuchElementException("Cliente no encontrado");
                    });
        } catch (IllegalArgumentException e) {
            logger.warn("❌ Error de validación: {}", e.getMessage(), e);
            throw e; // Manejado por GlobalExceptionHandler -> 400
        } catch (NoSuchElementException e) {
            logger.info("❌ Cliente no encontrado en la base de datos: {}", e.getMessage(), e);
            throw e; // Manejado por GlobalExceptionHandler -> 404
        } catch (Exception e) {
            logger.error("💥 Error inesperado al buscar cliente con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento, e);
            throw e; // Manejado por GlobalExceptionHandler -> 500
        }
    }
}
