package com.motavieirafelipe.order_management_microservice.dtos;

import com.motavieirafelipe.order_management_microservice.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record OrderDto (
    UUID orderId,
    UUID customerId,
    UUID deliveryId,
    OrderStatus status,
    Date creationDate,
    BigDecimal total,
    List<OrderProductDto> orderProducts
){
    public OrderDto (
        UUID customerId,
        UUID deliveryId,
        OrderStatus status,
        Date creationDate,
        BigDecimal total,
        List<OrderProductDto> orderProducts
    ){
        this(
            null,
            customerId,
            deliveryId,
            status,
            creationDate,
            total,
            orderProducts
        );
    }
}