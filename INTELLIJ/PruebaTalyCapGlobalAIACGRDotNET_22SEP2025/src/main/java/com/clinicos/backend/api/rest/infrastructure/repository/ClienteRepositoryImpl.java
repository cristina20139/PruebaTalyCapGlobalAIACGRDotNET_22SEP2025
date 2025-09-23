package com.clinicos.backend.api.rest.infrastructure.repository;

import com.clinicos.backend.api.rest.domain.model.Cliente;
import com.clinicos.backend.api.rest.domain.ports.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Implementación de {@link ClienteRepository} usando Spring JdbcTemplate.
 * <p>
 * Esta clase representa el adaptador de infraestructura para persistencia de clientes.
 * Cumple con los principios de <b>Arquitectura Hexagonal</b>, donde:
 * <ul>
 *   <li>El dominio (ClienteService) depende de la abstracción {@link ClienteRepository}.</li>
 *   <li>Esta clase implementa la infraestructura concreta (acceso a DB SQL Server).</li>
 * </ul>
 * <p>
 * Se aplican principios de <b>SOLID</b>:
 * <ul>
 *   <li><b>Single Responsibility Principle:</b> cada método tiene una única responsabilidad: obtener o insertar clientes.</li>
 *   <li><b>Open/Closed Principle:</b> puede extenderse sin modificar la lógica de negocio.</li>
 *   <li><b>Liskov Substitution Principle:</b> cumple la interfaz {@link ClienteRepository}.</li>
 *   <li><b>Interface Segregation Principle:</b> implementa solo métodos necesarios de la interfaz específica de clientes.</li>
 *   <li><b>Dependency Inversion Principle:</b> la capa de aplicación depende de la abstracción, no de la implementación concreta.</li>
 * </ul>
 * <p>
 * Se implementa <b>logging</b> usando SLF4J para trazabilidad de operaciones.
 *
 * @author Aura
 * Cristina Garzon Rodriguez
 * @since 23 Sep 2025
 */
@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private static final Logger logger = LoggerFactory.getLogger(ClienteRepositoryImpl.class);

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

    /**
     * Obtiene un cliente por tipo y número de documento.
     * <p>
     * Se registran logs de información, advertencia y errores.
     *
     * @param tipoDocumento Tipo de documento (CC, CE, Pasaporte, etc.). No puede ser {@code null} o vacío.
     * @param numeroDocumento Número de documento, debe ser mayor que 0.
     * @return {@link Optional} con el cliente si existe, vacío si no se encuentra.
     */
    @Override
    public Optional<Cliente> obtenerCliente(String tipoDocumento, long numeroDocumento) {
        String sql = "EXEC sp_ObtenerCliente @TipoDocumento = ?, @NumeroDocumento = ?";
        try {
            logger.info("🔍 Consultando cliente en DB con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento);
            Cliente cliente = jdbcTemplate.queryForObject(sql, clienteRowMapper, tipoDocumento, numeroDocumento);
            if (cliente != null) {
                logger.info("✅ Cliente encontrado en DB: {}", cliente);
                return Optional.of(cliente);
            } else {
                logger.warn("❌ Cliente no encontrado en DB con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("💥 Error al consultar cliente en DB con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento, e);
            return Optional.empty();
        }
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     * <p>
     * Se registran logs de inicio, éxito y errores.
     *
     * @param cliente Objeto {@link Cliente} a insertar. No puede ser {@code null}.
     */
    @Override
    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO Clientes (TipoDocumento, NumeroDocumento, PrimerNombre, SegundoNombre, PrimerApellido, SegundoApellido, Telefono, Direccion, CiudadResidencia) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            logger.info("📝 Insertando cliente en DB: {}", cliente);
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
            logger.info("✅ Cliente insertado correctamente: {}", cliente);
        } catch (Exception e) {
            logger.error("💥 Error al insertar cliente en DB: {}", cliente, e);
            throw e; // Re-lanzar para manejo global
        }
    }
}
