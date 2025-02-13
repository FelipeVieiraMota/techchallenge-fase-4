package br.com.fiap.cliente.gateway.database.jpa.repository;

import br.com.fiap.cliente.gateway.database.jpa.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
