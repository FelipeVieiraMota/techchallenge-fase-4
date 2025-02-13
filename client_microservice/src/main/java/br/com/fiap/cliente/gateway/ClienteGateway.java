package br.com.fiap.cliente.gateway;

import br.com.fiap.cliente.gateway.database.jpa.entity.ClienteEntity;

import java.util.Collection;

public interface ClienteGateway {

    Collection<ClienteEntity> findAllCliente();

    ClienteEntity findById(Long id);

    ClienteEntity save(ClienteEntity clienteEntity);

    void deleteById(Long id);

    boolean existsById(Long id);
}
