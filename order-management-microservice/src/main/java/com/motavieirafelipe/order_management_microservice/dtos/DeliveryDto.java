package com.motavieirafelipe.order_management_microservice.dtos;

import com.motavieirafelipe.order_management_microservice.enums.DeliveryStatus;

import java.util.Date;
import java.util.UUID;

public record DeliveryDto (
    UUID id,
    OrderDto order,
    DeliveryStatus deliveryStatus,
    Date departureDate,
    Date estimatedDeliveryDate,
    Date deliveryDate,
    String currentLocation
){
    public DeliveryDto (
        OrderDto order,
        DeliveryStatus deliveryStatus,
        Date departureDate,
        Date estimatedDeliveryDate,
        Date deliveryDate,
        String currentLocation
    ){
        this (
            null,
            order,
            deliveryStatus,
            departureDate,
            estimatedDeliveryDate,
            deliveryDate,
            currentLocation
        );
    }
}
