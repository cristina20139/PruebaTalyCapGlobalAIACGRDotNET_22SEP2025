package com.clinicos.backend.api.rest.domain.ports;

import com.clinicos.backend.api.rest.domain.model.Cliente;

import java.util.Optional;

/**
 * Puerto de persistencia para operaciones sobre {@link Cliente}.
 * <p>
 * Esta interfaz define los métodos que debe implementar cualquier repositorio de clientes,
 * siguiendo los principios de <b>arquitectura hexagonal</b>:
 * <ul>
 *   <li>La capa de dominio depende de abstracciones y no de implementaciones concretas.</li>
 *   <li>Se desacopla la lógica de negocio de la infraestructura (base de datos, JDBC, JPA, etc.).</li>
 * </ul>
 * <p>
 * Cumple con los principios de <b>SOLID</b>:
 * <ul>
 *   <li><b>Single Responsibility Principle:</b> cada método tiene una única responsabilidad: obtener o insertar clientes.</li>
 *   <li><b>Interface Segregation Principle:</b> la interfaz es pequeña y específica para operaciones de cliente.</li>
 *   <li><b>Dependency Inversion Principle:</b> la capa de aplicación depende de esta abstracción, no de implementaciones concretas.</li>
 * </ul>
 * <p>
 * Se recomienda implementar logs, manejo de errores y validaciones en las clases que implementen esta interfaz.
 *
 * @author Aura
 * Cristina Garzon Rodriguez
 * @since 23 Sep 2025
 */
public interface ClienteRepository {

    /**
     * Obtiene un cliente por tipo y número de documento.
     *
     * @param tipoDocumento Tipo de documento (CC, CE, Pasaporte, etc.). No puede ser {@code null} o vacío.
     * @param numeroDocumento Número de documento, mayor que 0.
     * @return {@link Optional} con el cliente si existe, vacío si no se encuentra.
     */
    Optional<Cliente> obtenerCliente(String tipoDocumento, long numeroDocumento);

    /**
     * Inserta un nuevo cliente en el repositorio.
     *
     * @param cliente Objeto {@link Cliente} a insertar. No puede ser {@code null}.
     */
    void insertarCliente(Cliente cliente); // nuevo método
}
