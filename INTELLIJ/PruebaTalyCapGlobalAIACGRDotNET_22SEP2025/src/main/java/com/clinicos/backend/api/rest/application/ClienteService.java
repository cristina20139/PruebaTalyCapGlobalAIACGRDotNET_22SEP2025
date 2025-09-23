package com.clinicos.backend.api.rest.application;

import com.clinicos.backend.api.rest.domain.model.Cliente;
import com.clinicos.backend.api.rest.domain.ports.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Obtiene un cliente por su tipo y número de documento.
     * <p>
     * Este método sigue los principios de <b>Clean Code</b> y <b>SOLID</b>:
     * <ul>
     *   <li><b>Single Responsibility Principle:</b> la función se enfoca únicamente en obtener un cliente y registrar los eventos de log.</li>
     *   <li><b>Open/Closed Principle:</b> puede extenderse con lógica adicional de filtrado o validación sin modificar el método principal.</li>
     *   <li><b>Liskov Substitution Principle:</b> retorna un {@code Optional<Cliente>}, manteniendo consistencia con la interfaz {@code ClienteRepository}.</li>
     *   <li><b>Interface Segregation Principle:</b> la dependencia {@code ClienteRepository} define solo los métodos necesarios para esta operación.</li>
     *   <li><b>Dependency Inversion Principle:</b> el método depende de abstracciones (repositorio) y no de implementaciones concretas.</li>
     * </ul>
     * <p>
     * El diseño respeta la <b>arquitectura hexagonal</b>, donde:
     * <ul>
     *   <li>El servicio (capa de aplicación) interactúa con el puerto {@code ClienteRepository}.</li>
     *   <li>La lógica de negocio permanece desacoplada del controlador y del repositorio concreto.</li>
     * </ul>
     * <p>
     * También se aplica un manejo robusto de logs:
     * <ul>
     *   <li>INFO: cuando se inicia la búsqueda y cuando se encuentra un cliente.</li>
     *   <li>WARN: cuando no se encuentra el cliente.</li>
     *   <li>ERROR: captura de excepciones inesperadas con re-lanzamiento para el GlobalExceptionHandler.</li>
     * </ul>
     *
     * @param tipoDocumento Tipo de documento del cliente (ej. CC, CE, Pasaporte).
     * @param numeroDocumento Número de documento del cliente.
     * @return Un {@code Optional<Cliente>} que contiene el cliente si se encuentra; {@code Optional.empty()} si no existe.
     * @throws RuntimeException Si ocurre un error inesperado durante la obtención del cliente.
     *
     * @author Aura Cristina
     * Garzon Rodriguez
     * @since 23 Sep 2025
     */    public Optional<Cliente> obtenerCliente(String tipoDocumento, long numeroDocumento) {
        logger.info("🔍 Buscando cliente con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento);

        try {
            Optional<Cliente> cliente = clienteRepository.obtenerCliente(tipoDocumento, numeroDocumento);
            if (cliente.isPresent()) {
                logger.info("✅ Cliente encontrado: {}", cliente.get());
            } else {
                logger.warn("❌ Cliente no encontrado con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento);
            }
            return cliente;
        } catch (Exception e) {
            logger.error("💥 Error al obtener cliente con tipoDocumento={} y numeroDocumento={}", tipoDocumento, numeroDocumento, e);
            throw e; // Re-lanzar para que el GlobalExceptionHandler lo capture
        }
    }

    /**
     * Crea un nuevo cliente en el sistema.
     * <p>
     * Este método sigue los principios de <b>Clean Code</b> y <b>SOLID</b>:
     * <ul>
     *   <li><b>Single Responsibility Principle:</b> se encarga únicamente de la creación de clientes y registro de logs.</li>
     *   <li><b>Open/Closed Principle:</b> se puede extender con lógica adicional (validaciones, auditoría) sin modificar el método base.</li>
     *   <li><b>Liskov Substitution Principle:</b> cumple con la interfaz {@code ClienteRepository} y puede ser sustituido por cualquier implementación de repositorio.</li>
     *   <li><b>Interface Segregation Principle:</b> depende solo de los métodos necesarios de {@code ClienteRepository}.</li>
     *   <li><b>Dependency Inversion Principle:</b> depende de abstracciones (repositorio) en lugar de implementaciones concretas.</li>
     * </ul>
     * <p>
     * Se respeta la <b>arquitectura hexagonal</b>, donde:
     * <ul>
     *   <li>La capa de aplicación (servicio) interactúa con el puerto {@code ClienteRepository}.</li>
     *   <li>La lógica de negocio está desacoplada de la infraestructura y controladores.</li>
     * </ul>
     * <p>
     * Manejo de logs:
     * <ul>
     *   <li>INFO: al iniciar la creación y al completar correctamente la operación.</li>
     *   <li>ERROR: captura de cualquier excepción durante la creación del cliente, re-lanzada para manejo global.</li>
     * </ul>
     *
     * @param cliente El objeto {@code Cliente} a crear. No puede ser {@code null}.
     * @throws RuntimeException Si ocurre un error inesperado durante la creación del cliente.
     *
     * @author Aura
     * Cristina Garzon Rodriguez
     * @since 23 Sep 2025
     */
    public void crearCliente(Cliente cliente) {
        logger.info("🆕 Creando cliente: {}", cliente);

        try {
            clienteRepository.insertarCliente(cliente);
            logger.info("✅ Cliente creado correctamente: {}", cliente);
        } catch (Exception e) {
            logger.error("💥 Error al crear cliente: {}", cliente, e);
            throw e; // Re-lanzar para manejo global
        }
    }
}
