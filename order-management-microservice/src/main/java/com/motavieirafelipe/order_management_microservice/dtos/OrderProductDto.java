package com.motavieirafelipe.order_management_microservice.dtos;

import java.util.UUID;

public record OrderProductDto (
    UUID id,
    UUID productId,
    int quantity,
    double unitPrice
){
    public OrderProductDto(
        UUID productId,
        int quantity,
        double unitPrice
    ) {
        this(
            null,
            productId,
            quantity,
            unitPrice
        );
    }
}
