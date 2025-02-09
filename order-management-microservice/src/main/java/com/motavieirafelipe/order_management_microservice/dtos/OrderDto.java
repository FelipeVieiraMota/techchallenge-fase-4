package com.motavieirafelipe.order_management_microservice.dtos;

import com.motavieirafelipe.order_management_microservice.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record OrderDto (
    UUID orderId,
    UUID customerId,
    OrderStatus status,
    Date creationDate,
    BigDecimal total,
    List<OrderProductDto> orderProducts,
    PaymentDto payment,
    DeliveryDto delivery
){
    public OrderDto (
        UUID customerId,
        OrderStatus status,
        Date creationDate,
        BigDecimal total,
        List<OrderProductDto> orderProducts,
        PaymentDto payment,
        DeliveryDto delivery
    ){
        this(
            null,
            customerId,
            status,
            creationDate,
            total,
            orderProducts,
            payment,
            delivery
        );
    }
}