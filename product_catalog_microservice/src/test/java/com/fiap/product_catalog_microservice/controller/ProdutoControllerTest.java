package com.fiap.product_catalog_microservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.product_catalog_microservice.enums.CategoriasProdutos;
import com.fiap.product_catalog_microservice.usecase.ProdutoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProdutoUseCase produtoUseCase;

    private ProdutoDTO produtoMock;
    private String produtoJSON;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        produtoMock = new ProdutoDTO(
                1L,
                "Ferro de Passar",
                "Ferro de Passar de alta qualidade para Eletrodomesticos.",
                105.99,
                200,
                CategoriasProdutos.ELETRODOMESTICOS);
        produtoJSON = formataProdutoparaJSON(produtoMock);
    }

    @Test
    void testBuscarProdutoPorId_Success() throws Exception {
        when(produtoUseCase.buscarProdutoPorId(1L)).thenReturn(produtoMock);

        mockMvc.perform(get("/v1/api/produto/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ferro de Passar"));
    }

    @Test
    void testBuscarProdutoPorId_NotFound() throws Exception {
        when(produtoUseCase.buscarProdutoPorId(1L)).thenReturn(null);

        mockMvc.perform(get("/v1/api/produto/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarTodosProdutos_Success() throws Exception {
        when(produtoUseCase.buscarTodosProdutos()).thenReturn(Collections.singletonList(produtoMock));

        mockMvc.perform(get("/v1/api/produto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ferro de Passar"));
    }

    @Test
    void testCriarProduto_Success() throws Exception {
        doNothing().when(produtoUseCase).criarProduto(any(ProdutoDTO.class));

        mockMvc.perform(post("/v1/api/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ferro de Passar"));
    }

    @Test
    void testCriarProduto_BadRequest() throws Exception {
        String invalidJSON = "{}"; // JSON inválido

        mockMvc.perform(post("/v1/api/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAtualizarProduto_Success() throws Exception {
        when(produtoUseCase.atualizarProduto(anyLong(), any(ProdutoDTO.class))).thenReturn(produtoMock);

        mockMvc.perform(put("/v1/api/produto/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ferro de Passar"));
    }

    @Test
    void testExcluirProduto_Success() throws Exception {
        doNothing().when(produtoUseCase).excluirProduto(1L);

        mockMvc.perform(delete("/v1/api/produto/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    private String formataProdutoparaJSON(ProdutoDTO produtoDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(produtoDTO);
    }
}
