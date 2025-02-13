package com.fiap.product_catalog_microservice.gateway;

import com.fiap.product_catalog_microservice.controller.ProdutoDTO;
import com.fiap.product_catalog_microservice.gateway.database.jpa.entity.ProdutoEntity;

import java.util.Collection;

public interface ProdutoGateway {

    Collection<ProdutoEntity> findAll();

    ProdutoEntity findById(Long id);

    ProdutoEntity update(Long id, ProdutoDTO produtoDTO);

    ProdutoEntity save(ProdutoDTO produtoDTO);

    void deleteById(Long id);

    boolean existById(Long id);
}
