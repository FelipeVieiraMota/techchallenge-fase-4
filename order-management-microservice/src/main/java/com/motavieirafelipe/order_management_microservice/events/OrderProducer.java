package com.motavieirafelipe.order_management_microservice.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motavieirafelipe.order_management_microservice.exceptions.OrderException;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class OrderProducer {

    private final StreamBridge streamBridge;

    public OrderProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) throws OrderException {
        try {
            streamBridge.send("orderCreated-out-0", event);
            System.out.println("orderCreated-out-0 : " + new ObjectMapper().writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new OrderException("Something wrong happened during send process ", INTERNAL_SERVER_ERROR);
        }
    }
}
