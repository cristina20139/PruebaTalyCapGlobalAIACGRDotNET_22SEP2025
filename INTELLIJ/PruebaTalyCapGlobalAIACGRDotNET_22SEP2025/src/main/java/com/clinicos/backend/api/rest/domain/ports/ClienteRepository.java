package com.clinicos.backend.api.rest.domain.ports;

import com.clinicos.backend.api.rest.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

    Optional<Cliente> obtenerCliente(String tipoDocumento, long numeroDocumento);
    void insertarCliente(Cliente cliente); // nuevo método

}
