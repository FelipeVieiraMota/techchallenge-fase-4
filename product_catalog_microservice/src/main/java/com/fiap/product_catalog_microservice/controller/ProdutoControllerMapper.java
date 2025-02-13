package com.fiap.product_catalog_microservice.controller;


import com.fiap.product_catalog_microservice.domain.Produto;
import com.fiap.product_catalog_microservice.gateway.database.jpa.entity.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProdutoControllerMapper {

    public ProdutoDTO toDTO(ProdutoEntity entity) {
        return new ProdutoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getQuantidadeEstoque(),
                entity.getCategoria()
        );
    }

    public ProdutoEntity toEntity(Produto prod) {
        return new ProdutoEntity(
                prod.getNome(),
                prod.getDescricao(),
                prod.getPreco(),
                prod.getQuantidadeEstoque(),
                prod.getCategoria()
        );
    }

    public Produto toDomain(ProdutoDTO entity) {
        return new Produto(
                entity.nome(),
                entity.descricao(),
                entity.preco(),
                entity.quantidadeEstoque(),
                entity.categoria()
        );
    }
}
