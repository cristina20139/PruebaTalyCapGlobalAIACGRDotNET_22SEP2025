package com.clinicos.backend.api.rest.application;

import com.clinicos.backend.api.rest.domain.model.Cliente;
import com.clinicos.backend.api.rest.domain.ports.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> obtenerCliente(String tipoDocumento, long numeroDocumento) {
        return clienteRepository.obtenerCliente(tipoDocumento, numeroDocumento);
    }

    // Crear cliente (insert)
    public void crearCliente(Cliente cliente) {
        clienteRepository.insertarCliente(cliente);
    }
}
