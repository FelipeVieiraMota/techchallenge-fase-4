package com.fiap.product_catalog_microservice.domain;

import com.fiap.product_catalog_microservice.enums.CategoriasProdutos;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public class Produto {

    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEstoque;
    private CategoriasProdutos categoria;

    public Produto(String nome, String descricao, double preco, int quantidadeEstoque, CategoriasProdutos categoria) {
        isValidoNome(nome);
        isValidoDescricao(descricao);
        isValidoPreco(preco);
        isValidoQuantidadeEstoque(quantidadeEstoque);
        isValidoCategoria(categoria);

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    private void isValidoQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade de estoque não pode ser negativa");
        }
    }

    private void isValidoPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
    }

    private void isValidoNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
    }

    private void isValidoDescricao(String descricao) {
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
    }

    private void isValidoCategoria(CategoriasProdutos categoria) {
        try {
            CategoriasProdutos.valueOf(String.valueOf(categoria));

        } catch (IllegalArgumentException e) {

            String categoriasPermitidas = Arrays.stream(CategoriasProdutos.values())
                    .map(Enum::name)
                    .collect(Collectors.joining("/"));

            throw new IllegalArgumentException("Valor diferente de " + categoriasPermitidas);
        }
    }

}
