package com.clinicos.backend;

import com.clinicos.backend.utility.ClienteGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación Spring Boot para gestión de clientes.
 * <p>
 * Esta clase representa el punto de entrada de la aplicación y configura la
 * generación inicial de datos de prueba mediante {@link ClienteGenerator}.
 * <p>
 * Cumple con principios de <b>Clean Code</b> y <b>SOLID</b>:
 * <ul>
 *   <li><b>Single Responsibility Principle:</b> responsabilidad única: iniciar la aplicación y definir el bean de seed.</li>
 *   <li><b>Dependency Inversion Principle:</b> la clase depende de abstracciones como {@link ClienteGenerator} en lugar de implementaciones concretas.</li>
 * </ul>
 * <p>
 * Se implementa <b>logging</b> con SLF4J para trazabilidad del arranque y de la generación de clientes.
 *
 * @author Aura
 * Cristina Garzon Rodriguez
 * @since 23 Sep 2025
 *
 * Para probar ir a Swagger http://localhost:8080/swagger-ui/index.html
 *
 */
@SpringBootApplication
public class ClientesApp {

    private static final Logger logger = LoggerFactory.getLogger(ClientesApp.class);

    /**
     * Método principal para iniciar la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientesApp.class, args);
        logger.info("🚀 ClientesApp iniciado correctamente");
    }

    /**
     * Bean de tipo {@link CommandLineRunner} que permite inicializar la base de datos
     * con datos de prueba si está habilitada la configuración de seed.
     * <p>
     * Se aplican logs de información, éxito y errores, siguiendo buenas prácticas
     * de trazabilidad.
     *
     * @param generator Generador de clientes {@link ClienteGenerator}.
     * @param seedEnabled Flag de configuración para habilitar la generación de clientes.
     * @param cantidad Número de clientes a generar si seedEnabled es {@code true}.
     * @return {@link CommandLineRunner} que ejecuta la generación al iniciar la app.
     */
    @Bean
    CommandLineRunner seedDatabase(
            ClienteGenerator generator,
            @Value("${clientes.seed.enabled:false}") boolean seedEnabled,
            @Value("${clientes.seed.cantidad:1000}") int cantidad
    ) {
        return args -> {
            if (seedEnabled) {
                logger.info("⚡ Generación de {} clientes iniciada por configuración", cantidad);
                try {
                    generator.generarClientes();
                    logger.info("✅ Generación de clientes completada exitosamente");
                } catch (Exception e) {
                    logger.error("💥 Error al generar clientes", e);
                    throw e; // Propaga para que Spring lo maneje
                }
            } else {
                logger.info("⚡ Generación de clientes deshabilitada por configuración");
            }
        };
    }
}
