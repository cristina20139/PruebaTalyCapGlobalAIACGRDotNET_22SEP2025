package infrastructure.repository;

import com.clinicos.backend.api.rest.domain.model.Cliente;
import com.clinicos.backend.api.rest.infrastructure.repository.ClienteRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteRepositoryImplTest {

    private JdbcTemplate jdbcTemplate;
    private ClienteRepositoryImpl clienteRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        clienteRepository = new ClienteRepositoryImpl(jdbcTemplate);
    }


    @Test
    void obtenerCliente_ShouldReturnEmpty_WhenNotFound() throws Exception {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq("CC"), eq(99999)))
                .thenThrow(new RuntimeException("No encontrado"));

        Optional<Cliente> result = clienteRepository.obtenerCliente("CC", 99999);

        assertFalse(result.isPresent());
    }

    @Test
    void insertarCliente_ShouldCallJdbcTemplateUpdate() {
        Cliente cliente = new Cliente("CC", 12345, "Juan", "Carlos", "Perez", "Gomez", "123456789", "Calle 1", "Bogotá");

        clienteRepository.insertarCliente(cliente);

        ArgumentCaptor<Object[]> captor = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate, times(1)).update(anyString(), captor.capture());

        Object[] values = captor.getValue();
        assertEquals("CC", values[0]);
        assertEquals(12345L, values[1]);
        assertEquals("Juan", values[2]);
        assertEquals("Bogotá", values[8]);
    }
}
