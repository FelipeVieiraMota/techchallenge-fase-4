package com.motavieirafelipe.delivery_logistic_microservice.consumer;

import com.motavieirafelipe.delivery_logistic_microservice.events.OrderCreatedEvent;
import com.motavieirafelipe.delivery_logistic_microservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final DeliveryService deliveryService;

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return deliveryService::processEventMessage;
    }
}