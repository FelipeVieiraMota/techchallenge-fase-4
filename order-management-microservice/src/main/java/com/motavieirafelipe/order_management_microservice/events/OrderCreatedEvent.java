package com.motavieirafelipe.order_management_microservice.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class OrderCreatedEvent {

    private UUID id;
    private UUID orderId;
    private UUID customerId;
    private List<ProductCreatedEvent> productCreatedEvents;
    private Date departureDate;
    private Date estimatedDeliveryDate;
    private Date deliveryDate;
    private String currentLocation;
}
