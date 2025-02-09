package com.motavieirafelipe.order_management_microservice.repositories;

import com.motavieirafelipe.order_management_microservice.entities.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {}