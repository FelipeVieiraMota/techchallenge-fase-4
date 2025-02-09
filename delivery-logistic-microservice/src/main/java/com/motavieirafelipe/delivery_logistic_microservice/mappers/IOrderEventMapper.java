package com.motavieirafelipe.delivery_logistic_microservice.mappers;

import com.motavieirafelipe.delivery_logistic_microservice.events.OrderCreatedEvent;
import com.motavieirafelipe.delivery_logistic_microservice.model.Delivery;

public interface IOrderEventMapper {
    Delivery toDelivery(OrderCreatedEvent event);
}
