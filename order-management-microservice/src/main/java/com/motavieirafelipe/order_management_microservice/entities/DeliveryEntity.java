package com.motavieirafelipe.order_management_microservice.entities;

import com.motavieirafelipe.order_management_microservice.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_delivery")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = false)
    private OrderEntity order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    private Date departureDate;
    private Date estimatedDeliveryDate;
    private Date deliveryDate;
    private String currentLocation;
}
