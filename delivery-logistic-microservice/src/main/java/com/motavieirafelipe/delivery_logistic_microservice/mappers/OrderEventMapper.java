package com.motavieirafelipe.delivery_logistic_microservice.mappers;

import com.motavieirafelipe.delivery_logistic_microservice.events.OrderCreatedEvent;
import com.motavieirafelipe.delivery_logistic_microservice.model.Delivery;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderEventMapper implements IOrderEventMapper
{
    public Delivery toDelivery(OrderCreatedEvent event)
    {
        final var delivery = new Delivery();
        delivery.setOrderId(event.getOrderId());
        delivery.setCustomerId(event.getCustomerId());
        delivery.setDeliveryStatus(event.getDeliveryStatus());
        delivery.setDepartureDate(event.getDepartureDate());
        delivery.setEstimatedDeliveryDate(event.getEstimatedDeliveryDate());
        delivery.setDeliveryDate(event.getDeliveryDate());
        delivery.setCurrentLocation(event.getCurrentLocation());

        if(!event.getProductCreatedEvents().isEmpty()) {
            var productId =  event.getProductCreatedEvents().get(0).getProductId() == null ?
                    UUID.randomUUID() : event.getProductCreatedEvents().get(0).getProductId();
            delivery.setProductId(productId);
        }

        return delivery;
    }
}
