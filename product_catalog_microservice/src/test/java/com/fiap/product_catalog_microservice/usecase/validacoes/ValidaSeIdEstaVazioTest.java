package com.fiap.product_catalog_microservice.usecase.validacoes;

import com.fiap.product_catalog_microservice.exceptions.NullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidaSeIdEstaVazioTest {

    private final ValidaSeIdEstaVazio validaSeIdEstaVazio = new ValidaSeIdEstaVazio();

    @Test
    void testValidarClienteQuandoIdNaoEstaVazio() {
        Long id = 1L;

        boolean resultado = validaSeIdEstaVazio.validarCliente(id);

        assertTrue(resultado);
    }

    @Test
    void testValidarClienteQuandoIdEstaVazio() {
        Long id = null;

        NullException exception = assertThrows(NullException.class, () -> validaSeIdEstaVazio.validarCliente(id));

        assertEquals("ID do produto não pode ser vazio.", exception.getReason());
    }
}
