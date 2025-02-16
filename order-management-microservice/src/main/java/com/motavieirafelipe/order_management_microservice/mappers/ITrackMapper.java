package com.motavieirafelipe.order_management_microservice.mappers;

import com.motavieirafelipe.order_management_microservice.dtos.TrackingDto;
import com.motavieirafelipe.order_management_microservice.entities.TrackingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITrackMapper {

    @Mapping(source = "order.orderId", target = "orderId") // ✅ Extract orderId from OrderEntity
    TrackingDto toDto(TrackingEntity entity);

    List<TrackingDto> toDtoList(List<TrackingEntity> entities);
}
