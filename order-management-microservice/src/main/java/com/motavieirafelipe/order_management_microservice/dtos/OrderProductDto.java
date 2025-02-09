package com.motavieirafelipe.order_management_microservice.dtos;

import java.util.UUID;

public record OrderProductDto (
    UUID id,
    OrderDto order,
    UUID productId,
    int quantity,
    double unitPrice
){
    public OrderProductDto(
        OrderDto order,
        UUID productId,
        int quantity,
        double unitPrice
    ) {
        this(
            null,
            order,
            productId,
            quantity,
            unitPrice
        );
    }
}
