package com.fiap.product_catalog_microservice.controller;

import com.fiap.product_catalog_microservice.enums.CategoriasProdutos;
import org.springframework.data.annotation.ReadOnlyProperty;

public record ProdutoDTO(
        @ReadOnlyProperty Long id,
        String nome,
        String descricao,
        double preco,
        int quantidadeEstoque,
        CategoriasProdutos categoria) {

}
