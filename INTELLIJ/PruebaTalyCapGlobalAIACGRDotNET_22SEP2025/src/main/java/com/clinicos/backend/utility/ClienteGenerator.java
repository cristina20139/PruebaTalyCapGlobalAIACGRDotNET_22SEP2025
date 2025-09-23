package com.clinicos.backend.utility;

import com.clinicos.backend.api.rest.application.ClienteService;
import com.clinicos.backend.api.rest.domain.model.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ClienteGenerator {

    private final ClienteService clienteService;
    private final Random random = new Random();

    @Value("${clientes.seed.enabled:false}")
    private boolean seedEnabled;

    @Value("${clientes.seed.cantidad:1000}")
    private int cantidad;

    private final String[] tiposDocumento = {"C", "P"}; // Cédula o Pasaporte

    private final String[] nombres = {
            "Sofía","Valentina","Isabella","Camila","María","Lucía","Martina","Emma","Daniela","Sara",
            "Juan","Sebastián","Mateo","Santiago","Samuel","Gabriel","Alejandro","David","Lucas","Nicolás",
            "Laura","Paula","Andrea","Juliana","Carolina","Diego","Carlos","Julián","Andrés",
            "Victoria","Mariana","Natalia","Mónica","Gabriela","Ana","Camilo","José","Fernando","Ricardo",
            "Manuela","Daniela","Emilia","Martín","Simón","Thiago","Javier","Felipe","Renata","Adrián",
            "Mario","Tomás","Bruno","Miguel","Alejandra","Claudia","Angela","Patricia","Luis","Antonio",
            "Jorge","Héctor","Diana","Carla","Lorena","Esteban","Felipe","Juan Pablo","José Miguel"
    };

    private final String[] apellidos = {
            "Gómez","Rodríguez","López","Martínez","Pérez","García","Sánchez","Ramírez","Torres","Flores",
            "Rojas","Morales","Cruz","Vásquez","Castillo","Alvarez","Mendoza","Gutiérrez","Ortiz","Silva",
            "González","Jiménez","Hernández","Chávez","Romero","Suárez","Bravo","Paredes","Salazar","Córdoba",
            "Castro","Acosta","Herrera","Rincón","Agudelo","Díaz","Soto","Cabrera","Peña","Navarro",
            "Ospina","Mejía","Arias","Velásquez","Cano","Montoya","Quintero","Medina","Reyes","Restrepo"
    };

    private final String[] ciudades = {
            "Bogotá","Medellín","Cali","Barranquilla","Cartagena","Cúcuta","Bucaramanga","Pereira","Santa Marta","Ibagué"
    };

    public ClienteGenerator(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void generarClientes() {
        if (!seedEnabled) {
            System.out.println("⚡ Generación de clientes deshabilitada por configuración");
            return;
        }

        for (int i = 1; i <= cantidad; i++) {
            String tipoDocumento = tiposDocumento[random.nextInt(tiposDocumento.length)];
            long numeroDocumento = 10000000L + random.nextInt(90000000); // entre 10M y 99,999,999

            String primerNombre = nombres[random.nextInt(nombres.length)];
            String segundoNombre = nombres[random.nextInt(nombres.length)];

            String primerApellido = apellidos[random.nextInt(apellidos.length)];
            String segundoApellido = apellidos[random.nextInt(apellidos.length)];

            String telefono = String.format("%03d-%07d", 300 + random.nextInt(1000), random.nextInt(10000000));

            String direccion = "Calle " + (random.nextInt(150) + 1) + " # " + (random.nextInt(100) + 1) + "-" + (random.nextInt(50) + 1);

            String ciudad = ciudades[random.nextInt(ciudades.length)];

            Cliente cliente = new Cliente(
                    tipoDocumento,
                    numeroDocumento,
                    primerNombre,
                    segundoNombre,
                    primerApellido,
                    segundoApellido,
                    telefono,
                    direccion,
                    ciudad
            );

            clienteService.crearCliente(cliente);
        }

        System.out.println("✅ Se han generado " + cantidad + " registros de prueba en la tabla Clientes");
    }
}