package com.fiap.product_catalog_microservice.batch.config;

import com.fiap.product_catalog_microservice.controller.ProdutoDTO;
import com.fiap.product_catalog_microservice.enums.CategoriasProdutos;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ProdutoDTOFieldSetMapper implements FieldSetMapper<ProdutoDTO> {
    @Override
    public ProdutoDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        return new ProdutoDTO(
                null,
                fieldSet.readString("nome"),
                fieldSet.readString("descricao"),
                fieldSet.readDouble("preco"),
                fieldSet.readInt("quantidadeEstoque"),
                CategoriasProdutos.valueOf(fieldSet.readString("categoria"))
        );
    }
}