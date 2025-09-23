package application;

import com.clinicos.backend.api.rest.application.ClienteService;
import com.clinicos.backend.api.rest.domain.model.Cliente;
import com.clinicos.backend.api.rest.domain.ports.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    private ClienteRepository clienteRepository;
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepository.class);
        clienteService = new ClienteService(clienteRepository);
    }

    @Test
    @DisplayName("✅ obtenerCliente debe devolver un cliente existente")
    void testObtenerClienteExistente() {
        // Arrange
        Cliente cliente = new Cliente(
                "CC", 123L,
                "Juan", "Carlos",
                "Pérez", "Gómez",
                "3001234567", "Calle 123", "Bogotá"
        );

        when(clienteRepository.obtenerCliente("CC", 123L))
                .thenReturn(Optional.of(cliente));

        // Act
        Optional<Cliente> resultado = clienteService.obtenerCliente("CC", 123L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getPrimerNombre());
        assertEquals("Pérez", resultado.get().getPrimerApellido());
        assertEquals("Bogotá", resultado.get().getCiudadResidencia());

        verify(clienteRepository, times(1)).obtenerCliente("CC", 123L);
    }

    @Test
    @DisplayName("❌ obtenerCliente debe devolver vacío si no existe")
    void testObtenerClienteNoExistente() {
        // Arrange
        when(clienteRepository.obtenerCliente("CC", 999L))
                .thenReturn(Optional.empty());

        // Act
        Optional<Cliente> resultado = clienteService.obtenerCliente("CC", 999L);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(clienteRepository, times(1)).obtenerCliente("CC", 999L);
    }

    @Test
    @DisplayName("🆕 crearCliente debe invocar insertarCliente en el repositorio")
    void testCrearCliente() {
        // Arrange
        Cliente cliente = new Cliente(
                "CE", 456L,
                "María", "Alejandra",
                "Gómez", "López",
                "3119876543", "Carrera 45", "Medellín"
        );

        // Act
        clienteService.crearCliente(cliente);

        // Assert
        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository, times(1)).insertarCliente(captor.capture());

        Cliente capturado = captor.getValue();
        assertEquals("CE", capturado.getTipoDocumento());
        assertEquals(456L, capturado.getNumeroDocumento());
        assertEquals("María", capturado.getPrimerNombre());
        assertEquals("Alejandra", capturado.getSegundoNombre());
        assertEquals("Medellín", capturado.getCiudadResidencia());
    }
}