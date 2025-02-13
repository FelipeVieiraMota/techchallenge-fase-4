package com.fiap.product_catalog_microservice.usecase.validacoes;

import com.fiap.product_catalog_microservice.exceptions.NullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidaSeIdEstaVazio implements ValidaProduto {
    @Override
    public boolean validarCliente(Long id) {

        if (id == null) {
            throw new NullException("ID do produto não pode ser vazio.");
        }
        return true;
    }
}
