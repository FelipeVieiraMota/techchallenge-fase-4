package com.fiap.product_catalog_microservice.gateway.database.jpa.repository;

import com.fiap.product_catalog_microservice.gateway.database.jpa.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
}
