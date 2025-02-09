package com.motavieirafelipe.order_management_microservice.repositories;

import com.motavieirafelipe.order_management_microservice.entities.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {}