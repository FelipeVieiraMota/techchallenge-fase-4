package com.motavieirafelipe.order_management_microservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_order_product")
public class OrderProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private OrderEntity order;

    // Reference to Product (via API, not FK in DB)
    private UUID productId;
    private int quantity;
    private double unitPrice;
}
