package com.clinicos.backend.api.rest.infrastructure.repository;

import com.clinicos.backend.api.rest.domain.model.Cliente;
import com.clinicos.backend.api.rest.domain.ports.ClienteRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClienteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Cliente> clienteRowMapper = new RowMapper<>() {
        @Override
        public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Cliente(
                    rs.getString("TipoDocumento"),
                    rs.getLong("NumeroDocumento"),
                    rs.getString("PrimerNombre"),
                    rs.getString("SegundoNombre"),
                    rs.getString("PrimerApellido"),
                    rs.getString("SegundoApellido"),
                    rs.getString("Telefono"),
                    rs.getString("Direccion"),
                    rs.getString("CiudadResidencia")
            );
        }
    };

    @Override
    public Optional<Cliente> obtenerCliente(String tipoDocumento, long numeroDocumento) {
        String sql = "EXEC sp_ObtenerCliente @TipoDocumento = ?, @NumeroDocumento = ?";
        try {
            Cliente cliente = jdbcTemplate.queryForObject(sql, clienteRowMapper, tipoDocumento, numeroDocumento);
            return Optional.ofNullable(cliente);
        } catch (Exception e) {
            return Optional.empty(); // No encontrado
        }
    }

    @Override
    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO Clientes (TipoDocumento, NumeroDocumento, PrimerNombre, SegundoNombre, PrimerApellido, SegundoApellido, Telefono, Direccion, CiudadResidencia) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                cliente.getTipoDocumento(),
                cliente.getNumeroDocumento(),
                cliente.getPrimerNombre(),
                cliente.getSegundoNombre(),
                cliente.getPrimerApellido(),
                cliente.getSegundoApellido(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getCiudadResidencia()
        );
    }
}