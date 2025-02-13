package com.fiap.product_catalog_microservice.gateway.database.jpa.entity;


import com.fiap.product_catalog_microservice.enums.CategoriasProdutos;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "tb_produto")
@Getter
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEstoque;

    @Enumerated(EnumType.STRING)
    private CategoriasProdutos categoria;

    public ProdutoEntity(String nome, String descricao, double preco, int quantidadeEstoque, CategoriasProdutos categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

    public void updateProduto(String nome, String descricao, double preco, int quantidadeEstoque, CategoriasProdutos categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
    }

}
