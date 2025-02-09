package com.motavieirafelipe.order_management_microservice.events;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final StreamBridge streamBridge;

    public OrderProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        streamBridge.send("orderCreated-out-0", event);
    }
}
