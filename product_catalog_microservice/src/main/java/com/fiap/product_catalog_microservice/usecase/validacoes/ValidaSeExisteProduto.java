package com.fiap.product_catalog_microservice.usecase.validacoes;

import com.fiap.product_catalog_microservice.exceptions.NotFoundException;
import com.fiap.product_catalog_microservice.gateway.ProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidaSeExisteProduto implements ValidaProduto {

    private final ProdutoGateway produtoGateway;

    @Override
    public boolean validarCliente(Long id) {
        if (!produtoGateway.existById(id)) {
            throw new NotFoundException("Não existe produto com o id informado.");
        }
        return true;
    }
}
