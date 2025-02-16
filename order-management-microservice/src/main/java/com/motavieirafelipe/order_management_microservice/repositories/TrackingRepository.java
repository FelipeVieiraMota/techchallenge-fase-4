package com.motavieirafelipe.order_management_microservice.repositories;

import com.motavieirafelipe.order_management_microservice.entities.TrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrackingRepository extends JpaRepository<TrackingEntity, UUID> {

    List<TrackingEntity> findByOrder_OrderId(UUID orderId);
}