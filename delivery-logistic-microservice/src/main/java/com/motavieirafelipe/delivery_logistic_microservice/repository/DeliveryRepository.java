package com.motavieirafelipe.delivery_logistic_microservice.repository;

import com.motavieirafelipe.delivery_logistic_microservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> { }
