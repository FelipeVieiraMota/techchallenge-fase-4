package com.fiap.product_catalog_microservice.controller;

import com.fiap.product_catalog_microservice.gateway.database.jpa.entity.ProdutoEntity;
import com.fiap.product_catalog_microservice.usecase.ProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoUseCase produtoUseCase;

    @Operation(summary = "Retorna todos os produtos")
    @ApiResponse(responseCode = "200", description = "Lista de produtos encontrados")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping
    public ResponseEntity<Collection<ProdutoDTO>> buscarTodosProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoUseCase.buscarTodosProdutos());
    }

    @Operation(summary = "Busca um produto pelo ID")
    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoEntity.class)))
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoUseCase.buscarProdutoPorId(id));
    }

    @Operation(summary = "Cria um novo produto")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoEntity.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválido")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PostMapping
    public ResponseEntity<ProdutoDTO> criarCliente(@Valid @RequestBody ProdutoDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoUseCase.criarProduto(clienteDTO));
    }

    @Operation(summary = "Atualiza produto")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoEntity.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválido")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO clienteDTO) {
        var result = produtoUseCase.atualizarProduto(id, clienteDTO);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Deleta produto")
    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaProduto(@PathVariable Long id) {
        produtoUseCase.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }
}
