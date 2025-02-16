package com.motavieirafelipe.order_management_microservice.mappers;

import com.motavieirafelipe.order_management_microservice.dtos.OrderDto;
import com.motavieirafelipe.order_management_microservice.dtos.OrderProductDto;
import com.motavieirafelipe.order_management_microservice.entities.OrderEntity;
import com.motavieirafelipe.order_management_microservice.entities.OrderProductEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IOrderMapper {

    OrderDto toDto(OrderEntity entity);

    OrderEntity toEntity(OrderDto dto);


    default List<OrderProductDto> orderProductEntityListToOrderProductDtoList(List<OrderProductEntity> orderProductEntities) {
        if (orderProductEntities == null) {
            return null;
        }
        return orderProductEntities.stream()
                .map(this::orderProductEntityToOrderProductDto)
                .collect(Collectors.toList());
    }

    default OrderProductDto orderProductEntityToOrderProductDto(OrderProductEntity orderProductEntity) {

        if (orderProductEntity == null) {
            return null;
        }

        final UUID id = orderProductEntity.getOrderProductId();
        final UUID productId = orderProductEntity.getProductId();
        final int quantity = orderProductEntity.getQuantity();
        final double unitPrice = orderProductEntity.getUnitPrice();

        return new OrderProductDto( id, productId, quantity, unitPrice );
    }
}
