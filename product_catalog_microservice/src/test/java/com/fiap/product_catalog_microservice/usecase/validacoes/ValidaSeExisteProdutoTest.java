package com.fiap.product_catalog_microservice.usecase.validacoes;

import com.fiap.product_catalog_microservice.exceptions.NotFoundException;
import com.fiap.product_catalog_microservice.gateway.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidaSeExisteProdutoTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private ValidaSeExisteProduto validaSeExisteProduto;

    @Test
    void testValidarClienteQuandoProdutoExiste() {
        Long id = 1L;
        when(produtoGateway.existById(id)).thenReturn(true);

        boolean resultado = validaSeExisteProduto.validarCliente(id);

        assertTrue(resultado);
        verify(produtoGateway, times(1)).existById(id);
    }

    @Test
    void testValidarClienteQuandoProdutoNaoExiste() {
        Long id = 1L;
        when(produtoGateway.existById(id)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> validaSeExisteProduto.validarCliente(id));

        assertEquals("Não existe produto com o id informado.", exception.getReason());
        verify(produtoGateway, times(1)).existById(id);
    }
}