package br.com.fiap.cliente.usecase;

import br.com.fiap.cliente.controller.ClienteDTO;
import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.ClienteGateway;
import br.com.fiap.cliente.gateway.database.jpa.entity.ClienteEntity;
import br.com.fiap.cliente.gateway.database.jpa.mapper.ClienteMapper;
import br.com.fiap.cliente.usecase.validacoes.ValidaClienteRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final ClienteMapper mapper;
    private final List<ValidaClienteRule> validaClienteRule;

    public Collection<ClienteDTO> findAllUsers() {
        return clienteGateway.findAllCliente().stream().map(mapper::toClienteDTO).toList();
    }

    public ClienteDTO findClienteById(Long id) {
        validarCliente(id);
        return mapper.toClienteDTO(clienteGateway.findById(id));
    }

    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        var clienteDomain = mapper.clienteDTOtoClienteDomain(clienteDTO);
        var clienteEntity = mapper.toClienteEntity(clienteDomain);
        return mapper.toClienteDTO(clienteGateway.save(clienteEntity));
    }

    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        validarCliente(id);

        var clienteExitente = clienteGateway.findById(id);
        var clienteNovo = mapper.clienteDTOtoClienteDomain(clienteDTO);

        atualizarDadosCliente(clienteExitente, clienteNovo);

        return mapper.toClienteDTO(clienteGateway.save(clienteExitente));
    }

    public void atualizarDadosCliente(ClienteEntity clienteExitente, Cliente clienteNovo) {
        clienteExitente.atualizarCliente(clienteNovo.getNome(), clienteNovo.getEmail(), clienteNovo.getTelefone(), clienteNovo.getEndereco());
    }

    public void deletaCliente(Long id) {
        validarCliente(id);
        clienteGateway.deleteById(id);
    }

    private void validarCliente(Long id) {
        validaClienteRule.forEach(j -> j.validarCliente(id));
    }

}
