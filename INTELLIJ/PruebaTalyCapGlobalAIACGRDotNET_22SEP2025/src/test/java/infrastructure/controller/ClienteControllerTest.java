package infrastructure.controller;

import com.clinicos.backend.api.rest.application.ClienteService;
import com.clinicos.backend.api.rest.domain.model.Cliente;
import com.clinicos.backend.api.rest.infrastructure.controller.ClienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    private ClienteService clienteService;
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        clienteService = mock(ClienteService.class);
        clienteController = new ClienteController(clienteService);
    }

    @Test
    void obtenerCliente_Success_200() {
        Cliente cliente = new Cliente("CC", 12345L, "Juan", "Carlos", "Pérez", "Gómez", "3001234567", "Calle 1", "Bogotá");
        when(clienteService.obtenerCliente("CC", 12345L)).thenReturn(Optional.of(cliente));

        ResponseEntity<Cliente> response = clienteController.obtenerCliente("CC", 12345L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
        verify(clienteService, times(1)).obtenerCliente("CC", 12345L);
    }

    @Test
    void obtenerCliente_BadRequest_400_TipoDocumentoVacio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteController.obtenerCliente("", 12345L)
        );

        assertEquals("El tipo de documento no puede estar vacío", exception.getMessage());
        verify(clienteService, never()).obtenerCliente(anyString(), anyLong());
    }

    @Test
    void obtenerCliente_BadRequest_400_NumeroDocumentoInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                clienteController.obtenerCliente("CC", 0L)
        );

        assertEquals("El número de documento debe ser mayor que 0", exception.getMessage());
        verify(clienteService, never()).obtenerCliente(anyString(), anyLong());
    }

    @Test
    void obtenerCliente_NotFound_404() {
        when(clienteService.obtenerCliente("CC", 12345L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                clienteController.obtenerCliente("CC", 12345L)
        );

        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(clienteService, times(1)).obtenerCliente("CC", 12345L);
    }

    @Test
    void obtenerCliente_InternalServerError_500() {
        when(clienteService.obtenerCliente("CC", 12345L)).thenThrow(new RuntimeException("DB caída"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                clienteController.obtenerCliente("CC", 12345L)
        );

        assertEquals("DB caída", exception.getMessage());
        verify(clienteService, times(1)).obtenerCliente("CC", 12345L);
    }
}
