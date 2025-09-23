package domain.model;

import com.clinicos.backend.api.rest.domain.model.Cliente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testConstructorAndGetters() {
        Cliente cliente = new Cliente(
                "CC",
                123456789L,
                "Juan",
                "Carlos",
                "Pérez",
                "Gómez",
                "3001234567",
                "Calle 123",
                "Bogotá"
        );

        assertEquals("CC", cliente.getTipoDocumento());
        assertEquals(123456789L, cliente.getNumeroDocumento());
        assertEquals("Juan", cliente.getPrimerNombre());
        assertEquals("Carlos", cliente.getSegundoNombre());
        assertEquals("Pérez", cliente.getPrimerApellido());
        assertEquals("Gómez", cliente.getSegundoApellido());
        assertEquals("3001234567", cliente.getTelefono());
        assertEquals("Calle 123", cliente.getDireccion());
        assertEquals("Bogotá", cliente.getCiudadResidencia());
    }

    @Test
    void testSetters() {
        Cliente cliente = new Cliente(
                "CC",
                123456789L,
                "Juan",
                "Carlos",
                "Pérez",
                "Gómez",
                "3001234567",
                "Calle 123",
                "Bogotá"
        );

        cliente.setTipoDocumento("TI");
        cliente.setNumeroDocumento(987654321L);
        cliente.setPrimerNombre("Ana");
        cliente.setSegundoNombre("María");
        cliente.setPrimerApellido("López");
        cliente.setSegundoApellido("Martínez");
        cliente.setTelefono("3109876543");
        cliente.setDireccion("Carrera 45");
        cliente.setCiudadResidencia("Medellín");

        assertEquals("TI", cliente.getTipoDocumento());
        assertEquals(987654321L, cliente.getNumeroDocumento());
        assertEquals("Ana", cliente.getPrimerNombre());
        assertEquals("María", cliente.getSegundoNombre());
        assertEquals("López", cliente.getPrimerApellido());
        assertEquals("Martínez", cliente.getSegundoApellido());
        assertEquals("3109876543", cliente.getTelefono());
        assertEquals("Carrera 45", cliente.getDireccion());
        assertEquals("Medellín", cliente.getCiudadResidencia());
    }
}
