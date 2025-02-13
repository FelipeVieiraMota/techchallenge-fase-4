package com.fiap.product_catalog_microservice.gateway.database.jpa;

import com.fiap.product_catalog_microservice.controller.ProdutoControllerMapper;
import com.fiap.product_catalog_microservice.controller.ProdutoDTO;
import com.fiap.product_catalog_microservice.domain.Produto;
import com.fiap.product_catalog_microservice.exceptions.NotFoundException;
import com.fiap.product_catalog_microservice.gateway.ProdutoGateway;
import com.fiap.product_catalog_microservice.gateway.database.jpa.entity.ProdutoEntity;
import com.fiap.product_catalog_microservice.gateway.database.jpa.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ProdutoJpaGateway implements ProdutoGateway {

    private final ProdutoRepository repository;
    private final ProdutoControllerMapper produtoControllerMapper;

    @Override
    public Collection<ProdutoEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public ProdutoEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não foi possível encontrar o produto com o ID: " + id + "."));
    }

    @Override
    public ProdutoEntity update(Long id, ProdutoDTO produtoDTO) {
        Produto produtoDomain = produtoControllerMapper.toDomain(produtoDTO);

        ProdutoEntity produtoEntity = findById(id);

        produtoEntity.updateProduto(
                produtoDomain.getNome(),
                produtoDomain.getDescricao(),
                produtoDomain.getPreco(),
                produtoDomain.getQuantidadeEstoque(),
                produtoDomain.getCategoria()
        );

        return repository.save(produtoEntity);
    }

    @Override
    public ProdutoEntity save(ProdutoDTO produtoDTO) {
        Produto produtoDomain = produtoControllerMapper.toDomain(produtoDTO);
        return repository.save(produtoControllerMapper.toEntity(produtoDomain));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return repository.existsById(id);
    }
}
