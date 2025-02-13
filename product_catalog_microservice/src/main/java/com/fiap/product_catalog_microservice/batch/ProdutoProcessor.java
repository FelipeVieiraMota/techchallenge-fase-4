package com.fiap.product_catalog_microservice.batch;

import com.fiap.product_catalog_microservice.controller.ProdutoControllerMapper;
import com.fiap.product_catalog_microservice.controller.ProdutoDTO;
import com.fiap.product_catalog_microservice.gateway.database.jpa.entity.ProdutoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class ProdutoProcessor implements ItemProcessor<ProdutoDTO, ProdutoEntity> {

    private final ProdutoControllerMapper mapper;

    @Override
    public ProdutoEntity process(ProdutoDTO item) throws Exception {
        var produto = mapper.toDomain(item);

        return mapper.toEntity(produto);
    }
}
