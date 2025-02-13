package com.fiap.product_catalog_microservice.usecase;

import com.fiap.product_catalog_microservice.controller.ProdutoControllerMapper;
import com.fiap.product_catalog_microservice.controller.ProdutoDTO;
import com.fiap.product_catalog_microservice.gateway.ProdutoGateway;
import com.fiap.product_catalog_microservice.usecase.validacoes.ValidaProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoUseCase {

    private final ProdutoControllerMapper produtoControllerMapper;
    private final ProdutoGateway gateway;
    private final List<ValidaProduto> validaProdutos;

    @Transactional(readOnly = true)
    public Collection<ProdutoDTO> buscarTodosProdutos() {
        return gateway.findAll().stream().map(produtoControllerMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoDTO buscarProdutoPorId(Long id) {
        validarCliente(id);
        return produtoControllerMapper.toDTO(gateway.findById(id));
    }
    
    @Transactional
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        validarCliente(id);
        return produtoControllerMapper.toDTO(gateway.update(id, produtoDTO));
    }

    @Transactional
    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO) {
        return produtoControllerMapper.toDTO(gateway.save(produtoDTO));
    }

    @Transactional
    public void excluirProduto(Long id) {
        validarCliente(id);
        gateway.deleteById(id);
    }

    private void validarCliente(Long id) {
        validaProdutos.forEach(p -> p.validarCliente(id));
    }
}
