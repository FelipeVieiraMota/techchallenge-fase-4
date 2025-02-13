package br.com.fiap.cliente.gateway.database.jpa.mapper;

import br.com.fiap.cliente.controller.ClienteDTO;
import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.database.jpa.entity.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteMapperTest {

    private ClienteMapper clienteMapper;
    private ClienteDTO clienteDTO;
    private Cliente cliente;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        clienteMapper = new ClienteMapper();
        clienteDTO = new ClienteDTO(1L, "teste","teste@teste.com", "(11)99999-9999", "Av. Paulista", LocalDate.now());
        cliente = new Cliente("Teste", "teste@teste.com", "(11)99999-9999", "Av. Paulista", LocalDate.now());
        clienteEntity = new ClienteEntity("Teste", "teste@teste.com", "(11)99999-9999", "Av. Paulista", LocalDate.now());
    }

    @Test
    void deveConverterClienteDTOParaClienteDomain() {
        Cliente result = clienteMapper.clienteDTOtoClienteDomain(clienteDTO);
        assertEquals(cliente, result);
    }

    @Test
    void deveConverterClienteParaClienteEntity() {
        ClienteEntity result = clienteMapper.toClienteEntity(cliente);
        assertEquals(clienteEntity, result);
    }

    @Test
    void deveConverterClienteEntityParaClienteDTO() {
        ClienteDTO result = clienteMapper.toClienteDTO(clienteEntity);
        assertEquals(clienteDTO, result);
    }
}