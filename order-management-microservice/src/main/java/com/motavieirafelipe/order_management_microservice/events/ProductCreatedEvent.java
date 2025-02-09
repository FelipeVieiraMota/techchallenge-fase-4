package com.motavieirafelipe.order_management_microservice.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ProductCreatedEvent {
    private UUID productId;
    private int quantity;
    private double unitPrice;
}
