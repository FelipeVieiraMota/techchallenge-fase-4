package com.motavieirafelipe.order_management_microservice.entities;

import com.motavieirafelipe.order_management_microservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    // Reference to Customer (via API, not FK in DB)
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;// Default status when creating an order

    private Date creationDate;
    private BigDecimal total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProductEntity> orderProducts;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private PaymentEntity payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private DeliveryEntity delivery;
}