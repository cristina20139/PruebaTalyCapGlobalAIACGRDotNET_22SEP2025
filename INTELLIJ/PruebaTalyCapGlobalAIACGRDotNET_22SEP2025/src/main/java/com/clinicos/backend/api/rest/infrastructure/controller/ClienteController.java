package com.clinicos.backend.api.rest.infrastructure.controller;

import com.clinicos.backend.api.rest.application.ClienteService;
import com.clinicos.backend.api.rest.domain.model.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
     * @return 200 OK si se encuentra, 404 Not Found si no existe
     */
    //@Operation(summary = "Obtener un cliente por tipo y número de documento")
    @GetMapping("/{tipoDocumento}/{numeroDocumento}")
    public Cliente obtenerCliente(
            @PathVariable String tipoDocumento,
            @PathVariable long numeroDocumento
    ) {
        return clienteService.obtenerCliente(tipoDocumento, numeroDocumento).orElse(null);
    }
}
