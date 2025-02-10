package com.motavieirafelipe.order_management_microservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motavieirafelipe.order_management_microservice.dtos.OrderDto;
import com.motavieirafelipe.order_management_microservice.entities.OrderEntity;
import com.motavieirafelipe.order_management_microservice.entities.OrderProductEntity;
import com.motavieirafelipe.order_management_microservice.enums.OrderStatus;
import com.motavieirafelipe.order_management_microservice.events.OrderCreatedEvent;
import com.motavieirafelipe.order_management_microservice.events.OrderProducer;
import com.motavieirafelipe.order_management_microservice.exceptions.OrderException;
import com.motavieirafelipe.order_management_microservice.mappers.IOrderCreatedEventMapper;
import com.motavieirafelipe.order_management_microservice.mappers.IOrderMapper;
import com.motavieirafelipe.order_management_microservice.repositories.IOrderProductRepository;
import com.motavieirafelipe.order_management_microservice.repositories.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderOrchestrator {

    private final IOrderProductRepository orderProductRepository;
    private final IOrderRepository orderRepository;
    private final IOrderMapper orderMapper;
    private final OrderProducer orderProducer;
    private final IOrderCreatedEventMapper orderCreatedEventMapper;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) throws OrderException {

        final var order = orderMapper.toEntity(orderDto);

        order.setStatus(OrderStatus.CREATED);

        Optional.ofNullable(order.getOrderProducts())
                .ifPresent(products -> products.forEach(product -> product.setOrder(order)));

        final var createdOrder = orderMapper.toDto(orderRepository.save(order));

        pushOrderToQueue(createdOrder);

        return createdOrder;
    }

    public void pushOrderToQueue(OrderDto createdOrder) throws OrderException {

        try {
            orderProducer.sendOrderCreatedEvent(orderCreatedEventMapper.toEvent(createdOrder));
            System.out.println("Event Sent  : " + new ObjectMapper().writeValueAsString(createdOrder));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    public OrderDto getOrderById(UUID orderId) throws OrderException {
        return orderMapper.toDto(
                orderRepository.findById(orderId)
                    .orElseThrow(()->new OrderException("Order " + orderId + " not found.", NOT_FOUND))
        );
    }

    @Transactional
    public OrderDto updateOrder(UUID orderId, OrderDto updatedOrderDto) throws OrderException {

        final OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(()->new OrderException("Order " + orderId + " not found.", NOT_FOUND));

        existingOrder.setCustomerId(updatedOrderDto.customerId());
        existingOrder.setTotal(updatedOrderDto.total());

        // Update Order Products
        if (updatedOrderDto.orderProducts() != null) {
            // Remove old products and add new ones
            orderProductRepository.deleteAll(existingOrder.getOrderProducts());

            final List<OrderProductEntity> updatedProducts = updatedOrderDto.orderProducts().stream()
                    .map(dto -> {
                        OrderProductEntity productEntity = new OrderProductEntity();
                        productEntity.setOrder(existingOrder);
                        productEntity.setProductId(dto.productId());
                        productEntity.setQuantity(dto.quantity());
                        productEntity.setUnitPrice(dto.unitPrice());
                        return productEntity;
                    })
                    .collect(Collectors.toList());

            existingOrder.setOrderProducts(updatedProducts);
            orderProductRepository.saveAll(updatedProducts);
        }

        final var updatedOrder = orderMapper.toDto(orderRepository.save(existingOrder));

        pushOrderToQueue(updatedOrder);

        return updatedOrder;
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
