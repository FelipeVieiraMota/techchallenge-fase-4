package br.com.fiap.cliente.usercase;

import br.com.fiap.cliente.controller.ClienteDTO;
import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.ClienteGateway;
import br.com.fiap.cliente.gateway.database.jpa.entity.ClienteEntity;
import br.com.fiap.cliente.gateway.database.jpa.mapper.ClienteMapper;
import br.com.fiap.cliente.usecase.ClienteUseCase;
import br.com.fiap.cliente.usecase.validacoes.ValidaClienteRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteUseCaseTest {

    @Mock
    private ClienteGateway clienteGateway;

    @Mock
    private ClienteMapper mapper;

    @Mock
    private List<ValidaClienteRule> validaClienteRule;

    @InjectMocks
    private ClienteUseCase clienteUseCase;

    private ClienteEntity clienteEntity;
    private ClienteDTO clienteDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteEntity = new ClienteEntity("Teste", "teste@teste.com", "(11)99999-9999", "Av. Paulista", LocalDate.now());
        clienteDTO = new ClienteDTO(1L, "teste","teste@teste.com", "(11)99999-9999", "Av. Paulista", LocalDate.now());
        cliente = new Cliente("Teste", "teste@teste.com", "(11)99999-9999", "Av. Paulista", LocalDate.now());
    }

    @Nested
    class FindUsersTests {

        @Test
        void deveRetornarTodosOsClientes() {
            when(clienteGateway.findAllCliente()).thenReturn(List.of(clienteEntity));

            var result = clienteUseCase.findAllUsers();

            assertEquals(1, result.size());
            verify(clienteGateway, times(1)).findAllCliente();
            verify(mapper, times(1)).toClienteDTO(any(ClienteEntity.class));
        }

        @Test
        void deveRetornarClientePorId() {

            when(clienteGateway.findById(anyLong())).thenReturn(clienteEntity);
            when(mapper.toClienteDTO(any(ClienteEntity.class))).thenReturn(clienteDTO);

            var result = clienteUseCase.findClienteById(1L);

            assertEquals(clienteDTO, result);
            verify(clienteGateway, times(1)).findById(anyLong());
            verify(mapper, times(1)).toClienteDTO(any(ClienteEntity.class));
        }
    }

    @Nested
    class CriarClienteTests {

        @Test
        void deveCriarCliente() {

            when(mapper.clienteDTOtoClienteDomain(any(ClienteDTO.class))).thenReturn(cliente);
            when(mapper.toClienteEntity(any(Cliente.class))).thenReturn(clienteEntity);
            when(clienteGateway.save(any(ClienteEntity.class))).thenReturn(clienteEntity);
            when(mapper.toClienteDTO(any(ClienteEntity.class))).thenReturn(clienteDTO);

            var result = clienteUseCase.criarCliente(clienteDTO);

            assertEquals(clienteDTO, result);
            verify(mapper, times(1)).clienteDTOtoClienteDomain(any(ClienteDTO.class));
            verify(mapper, times(1)).toClienteEntity(any(Cliente.class));
            verify(clienteGateway, times(1)).save(any(ClienteEntity.class));
            verify(mapper, times(1)).toClienteDTO(any(ClienteEntity.class));
        }
    }


    @Nested
    class UpdateClienteTests {

        @Test
        void deveAtualizarCliente() {

            when(clienteGateway.findById(anyLong())).thenReturn(clienteEntity);
            when(mapper.clienteDTOtoClienteDomain(any(ClienteDTO.class))).thenReturn(cliente);
            when(mapper.toClienteEntity(any(Cliente.class))).thenReturn(clienteEntity);
            when(mapper.toClienteDTO(any(ClienteEntity.class))).thenReturn(clienteDTO);
            when(clienteGateway.save(any(ClienteEntity.class))).thenReturn(clienteEntity);


            var result = clienteUseCase.updateCliente(1L, clienteDTO);

            assertEquals(clienteDTO, result);
            verify(clienteGateway, times(1)).findById(anyLong());
            verify(mapper, times(2)).clienteDTOtoClienteDomain(any(ClienteDTO.class));
            verify(mapper, times(1)).toClienteEntity(any(Cliente.class));
            verify(mapper, times(2)).toClienteDTO(any(ClienteEntity.class));
            verify(clienteGateway, times(1)).save(any(ClienteEntity.class));

        }
    }

    @Nested
    class DeletaClienteTests {

        @Test
        void deveDeletarCliente() {
            doNothing().when(clienteGateway).deleteById(anyLong());

            clienteUseCase.deletaCliente(1L);

            verify(clienteGateway, times(1)).deleteById(anyLong());
        }
    }
}
