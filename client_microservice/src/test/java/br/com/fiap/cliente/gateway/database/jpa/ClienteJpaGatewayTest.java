package br.com.fiap.cliente.gateway.database.jpa;

import br.com.fiap.cliente.exception.NotFoundException;
import br.com.fiap.cliente.gateway.database.jpa.entity.ClienteEntity;
import br.com.fiap.cliente.gateway.database.jpa.repository.ClienteRepository;
import br.com.fiap.cliente.usecase.validacoes.ValidaClienteRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClienteJpaGatewayTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private List<ValidaClienteRule> validaClienteRule;

    @InjectMocks
    private ClienteJpaGateway clienteJpaGateway;

    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteEntity = new ClienteEntity("Teste", "teste@teste.com", "(11)99999-9999", "Av. Paulista", LocalDate.now());
    }

    @Test
    void deveEncontrarClientePorId() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(clienteEntity));

        ClienteEntity result = clienteJpaGateway.findById(1L);

        assertEquals(clienteEntity, result);
        verify(clienteRepository, times(1)).findById(anyLong());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clienteJpaGateway.findById(1L));
        verify(clienteRepository, times(1)).findById(anyLong());
    }

    @Test
    void deveSalvarCliente() {
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        ClienteEntity result = clienteJpaGateway.save(clienteEntity);

        assertEquals(clienteEntity, result);
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    void deveDeletarClientePorId() {
        doNothing().when(clienteRepository).deleteById(anyLong());

        clienteJpaGateway.deleteById(1L);

        verify(clienteRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deveVerificarSeClienteExistePorId() {
        when(clienteRepository.existsById(anyLong())).thenReturn(true);

        boolean exists = clienteJpaGateway.existsById(1L);

        assertTrue(exists);
        verify(clienteRepository, times(1)).existsById(anyLong());
    }
}