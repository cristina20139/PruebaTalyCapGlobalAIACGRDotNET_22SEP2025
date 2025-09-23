package com.clinicos.backend;

import com.clinicos.backend.utility.ClienteGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientesApp {

    public static void main(String[] args) {
        SpringApplication.run(ClientesApp.class, args);
    }

    @Bean
    CommandLineRunner seedDatabase(
            ClienteGenerator generator,
            @Value("${clientes.seed.enabled:false}") boolean seedEnabled,
            @Value("${clientes.seed.cantidad:1000}") int cantidad
    ) {
        return args -> {
            if (seedEnabled) {
                generator.generarClientes();
            } else {
                System.out.println("⚡ Generación de clientes deshabilitada por configuración");
            }
        };
    }
}

