package com.motavieirafelipe.delivery_logistic_microservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motavieirafelipe.delivery_logistic_microservice.events.OrderCreatedEvent;
import com.motavieirafelipe.delivery_logistic_microservice.mappers.IOrderEventMapper;
import com.motavieirafelipe.delivery_logistic_microservice.model.Delivery;
import com.motavieirafelipe.delivery_logistic_microservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final IOrderEventMapper mapper;

    @Transactional
    public void processEventMessage(OrderCreatedEvent message) {
        try {
            final Delivery delivery = mapper.toDelivery(message);

            final var saved = deliveryRepository.save(delivery);

            System.out.println("Saved data " + new ObjectMapper().writeValueAsString(saved));
        }
        catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }
}
