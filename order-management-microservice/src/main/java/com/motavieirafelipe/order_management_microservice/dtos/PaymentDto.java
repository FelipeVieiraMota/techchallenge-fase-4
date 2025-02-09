package com.motavieirafelipe.order_management_microservice.dtos;

import com.motavieirafelipe.order_management_microservice.enums.PaymentMethod;
import com.motavieirafelipe.order_management_microservice.enums.PaymentStatus;

import java.util.Date;
import java.util.UUID;

public record PaymentDto (
    UUID id,
    OrderDto order,
    PaymentMethod paymentMethod,
    PaymentStatus status,
    Date paymentDate
){
    public PaymentDto(
            OrderDto order,
            PaymentMethod paymentMethod,
            PaymentStatus status,
            Date paymentDate
    ){
        this(
            null,
            order,
            paymentMethod,
            status,
            paymentDate
        );
    }
}
