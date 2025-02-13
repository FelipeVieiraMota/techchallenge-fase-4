package br.com.fiap.cliente.gateway.database.jpa.mapper;

import br.com.fiap.cliente.controller.ClienteDTO;
import br.com.fiap.cliente.domain.Cliente;
import br.com.fiap.cliente.gateway.database.jpa.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente clienteDTOtoClienteDomain(ClienteDTO clienteDTO) {
        return new Cliente(
                clienteDTO.nome(),
                clienteDTO.email(),
                clienteDTO.telefone(),
                clienteDTO.endereco(),
                clienteDTO.dataCadastro());
    }

    public ClienteEntity toClienteEntity(Cliente cliente) {
        return new ClienteEntity(
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getDataCadastro());
    }

    public ClienteDTO toClienteDTO(ClienteEntity clienteEntity) {
        return new ClienteDTO(
                clienteEntity.getId(),
                clienteEntity.getNome(),
                clienteEntity.getEmail(),
                clienteEntity.getTelefone(),
                clienteEntity.getEndereco(),
                clienteEntity.getDataCadastro()
        );
    }
}
