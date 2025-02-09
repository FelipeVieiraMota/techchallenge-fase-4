package com.motavieirafelipe.order_management_microservice.mappers;

import com.motavieirafelipe.order_management_microservice.dtos.OrderDto;
import com.motavieirafelipe.order_management_microservice.dtos.OrderProductDto;
import com.motavieirafelipe.order_management_microservice.events.OrderCreatedEvent;
import com.motavieirafelipe.order_management_microservice.events.ProductCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderCreatedEventMapper {

    @Mapping(source = "orderProducts", target = "productCreatedEvents")
    @Mapping(source = "status", target = "deliveryStatus")
    @Mapping(source = "delivery.departureDate", target = "departureDate")
    @Mapping(source = "delivery.estimatedDeliveryDate", target = "estimatedDeliveryDate")
    @Mapping(source = "delivery.deliveryDate", target = "deliveryDate")
    @Mapping(source = "delivery.currentLocation", target = "currentLocation")
    OrderCreatedEvent toEvent(OrderDto createdOrder);

    ProductCreatedEvent toProductEvent(OrderProductDto orderProductDto);

    List<ProductCreatedEvent> toProductEventList(List<OrderProductDto> orderProductDtoList);
}
