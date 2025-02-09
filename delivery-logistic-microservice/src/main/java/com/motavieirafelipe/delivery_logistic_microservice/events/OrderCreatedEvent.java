package com.motavieirafelipe.delivery_logistic_microservice.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.motavieirafelipe.delivery_logistic_microservice.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCreatedEvent implements Serializable {

    private UUID id;
    private UUID orderId;
    private UUID customerId;
    private List<ProductCreatedEvent> productCreatedEvents;
    private DeliveryStatus deliveryStatus;
    private Date departureDate;
    private Date estimatedDeliveryDate;
    private Date deliveryDate;
    private String currentLocation;

    public OrderCreatedEvent(
        UUID orderId,
        UUID customerId,
        List<ProductCreatedEvent> productCreatedEvents,
        DeliveryStatus deliveryStatus,
        Date departureDate,
        Date estimatedDeliveryDate,
        Date deliveryDate,
        String currentLocation
    ){
        this.orderId = orderId;
        this.customerId = customerId;
        this.productCreatedEvents = productCreatedEvents;
        this.deliveryStatus = deliveryStatus;
        this.departureDate = departureDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.deliveryDate = deliveryDate;
        this.currentLocation = currentLocation;
    }

}
