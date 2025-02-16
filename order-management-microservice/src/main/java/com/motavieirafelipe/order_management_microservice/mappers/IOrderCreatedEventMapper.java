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
    OrderCreatedEvent toEvent(OrderDto createdOrder);

    ProductCreatedEvent toProductEvent(OrderProductDto orderProductDto);

    List<ProductCreatedEvent> toProductEventList(List<OrderProductDto> orderProductDtoList);
}
