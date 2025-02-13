package com.fiap.product_catalog_microservice.usecase;

import com.fiap.product_catalog_microservice.controller.ProdutoControllerMapper;
import com.fiap.product_catalog_microservice.controller.ProdutoDTO;
import com.fiap.product_catalog_microservice.enums.CategoriasProdutos;
import com.fiap.product_catalog_microservice.gateway.database.jpa.ProdutoJpaGateway;
import com.fiap.product_catalog_microservice.gateway.database.jpa.entity.ProdutoEntity;
import com.fiap.product_catalog_microservice.usecase.validacoes.ValidaProduto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoUseCaseTest {

    @Mock
    private ProdutoControllerMapper produtoControllerMapper;

    @Mock
    private ProdutoJpaGateway gateway;

    @Mock
    private List<ValidaProduto> validaProdutos;

    @InjectMocks
    private ProdutoUseCase produtoUseCase;

    private ProdutoDTO produtoDTO;
    private ProdutoEntity produtoEntity;

    @BeforeEach
    void setUp() {
        produtoDTO = new ProdutoDTO(1L, "Produto A", "Descrição A", 100.0, 10, CategoriasProdutos.CASA_E_DECORACAO);
        produtoEntity = new ProdutoEntity("Produto A", "Descrição A", 100.0, 10, CategoriasProdutos.ELETRODOMESTICOS);
    }

    @Test
    void testBuscarTodosProdutos() {
        when(gateway.findAll()).thenReturn(Collections.singletonList(produtoEntity));
        when(produtoControllerMapper.toDTO(produtoEntity)).thenReturn(produtoDTO);

        Collection<ProdutoDTO> resultado = produtoUseCase.buscarTodosProdutos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(produtoDTO, resultado.iterator().next());
        verify(gateway, times(1)).findAll();
        verify(produtoControllerMapper, times(1)).toDTO(produtoEntity);
    }

    @Test
    void testBuscarProdutoPorId() {
        Long id = 1L;
        when(gateway.findById(id)).thenReturn(produtoEntity);
        when(produtoControllerMapper.toDTO(produtoEntity)).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoUseCase.buscarProdutoPorId(id);

        assertNotNull(resultado);
        assertEquals(produtoDTO, resultado);
        verify(gateway, times(1)).findById(id);
        verify(produtoControllerMapper, times(1)).toDTO(produtoEntity);
    }

    @Test
    void testAtualizarProduto() {
        Long id = 1L;
        when(gateway.update(id, produtoDTO)).thenReturn(produtoEntity);
        when(produtoControllerMapper.toDTO(produtoEntity)).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoUseCase.atualizarProduto(id, produtoDTO);

        assertNotNull(resultado);
        assertEquals(produtoDTO, resultado);
        verify(gateway, times(1)).update(id, produtoDTO);
        verify(produtoControllerMapper, times(1)).toDTO(produtoEntity);
    }

    @Test
    void testCriarProduto() {
        when(gateway.save(produtoDTO)).thenReturn(produtoEntity);
        when(produtoControllerMapper.toDTO(produtoEntity)).thenReturn(produtoDTO);

        ProdutoDTO resultado = produtoUseCase.criarProduto(produtoDTO);

        assertNotNull(resultado);
        assertEquals(produtoDTO, resultado);
        verify(gateway, times(1)).save(produtoDTO);
        verify(produtoControllerMapper, times(1)).toDTO(produtoEntity);
    }

    @Test
    void testExcluirProduto() {
        Long id = 1L;
        doNothing().when(gateway).deleteById(id);

        produtoUseCase.excluirProduto(id);

        verify(gateway, times(1)).deleteById(id);
    }
}
