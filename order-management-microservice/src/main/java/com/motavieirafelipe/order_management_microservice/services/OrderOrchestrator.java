package com.motavieirafelipe.order_management_microservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motavieirafelipe.order_management_microservice.dtos.OrderDto;
import com.motavieirafelipe.order_management_microservice.dtos.TrackingDto;
import com.motavieirafelipe.order_management_microservice.entities.OrderEntity;
import com.motavieirafelipe.order_management_microservice.entities.OrderProductEntity;
import com.motavieirafelipe.order_management_microservice.entities.TrackingEntity;
import com.motavieirafelipe.order_management_microservice.enums.OrderStatus;
import com.motavieirafelipe.order_management_microservice.events.OrderProducer;
import com.motavieirafelipe.order_management_microservice.exceptions.OrderException;
import com.motavieirafelipe.order_management_microservice.mappers.IOrderCreatedEventMapper;
import com.motavieirafelipe.order_management_microservice.mappers.IOrderMapper;
import com.motavieirafelipe.order_management_microservice.mappers.ITrackMapper;
import com.motavieirafelipe.order_management_microservice.repositories.IOrderProductRepository;
import com.motavieirafelipe.order_management_microservice.repositories.IOrderRepository;
import com.motavieirafelipe.order_management_microservice.repositories.TrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderOrchestrator {

    private final IOrderProductRepository orderProductRepository;
    private final IOrderRepository orderRepository;
    private final IOrderMapper orderMapper;
    private final OrderProducer orderProducer;
    private final IOrderCreatedEventMapper orderCreatedEventMapper;
    private final TrackingRepository trackingRepository;
    private final ITrackMapper iTrackMapper;


    private static final Map<OrderStatus, Set<OrderStatus>> validStatusTransitions = new EnumMap<>(OrderStatus.class);

    static {
        validStatusTransitions.put(OrderStatus.CREATED, Set.of(OrderStatus.PROCESSING, OrderStatus.CANCELED));
        validStatusTransitions.put(OrderStatus.PROCESSING, Set.of(OrderStatus.SHIPPED, OrderStatus.CANCELED));
        validStatusTransitions.put(OrderStatus.SHIPPED, Set.of(OrderStatus.DELIVERED));
        // Delivered orders cannot change status
        validStatusTransitions.put(OrderStatus.DELIVERED, Set.of());
        // Canceled orders cannot change status
        validStatusTransitions.put(OrderStatus.CANCELED, Set.of());
    }

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

    public Page<OrderDto> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size)).map(orderMapper::toDto);
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

        if (!isValidStatusChange(orderId, updatedOrderDto.status())) {
            throw new OrderException("Invalid order status transition " + updatedOrderDto.status(), BAD_REQUEST);
        }

        existingOrder.setCustomerId(updatedOrderDto.customerId());
        existingOrder.setTotal(updatedOrderDto.total());
        existingOrder.setStatus(updatedOrderDto.status());

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

    public boolean isValidStatusChange(UUID orderId, OrderStatus newStatus) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    return validStatusTransitions.getOrDefault(order.getStatus(), Set.of()).contains(newStatus);
                })
                .orElse(false);
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }

    @Transactional
    public void trackOrder(UUID orderId, TrackingDto dto) throws OrderException {

        double latitude = dto.latitude();
        double longitude = dto.longitude();

        final OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order " + orderId + " not found.", HttpStatus.NOT_FOUND));

        final TrackingEntity tracking = TrackingEntity.builder()
                .order(existingOrder)
                .latitude(latitude)
                .longitude(longitude)
                .timestamp(LocalDateTime.now())
                .build();

        trackingRepository.save(tracking);
    }

    public List<TrackingDto> getTrackById(UUID orderId) throws OrderException {

        final OrderEntity existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order " + orderId + " not found.", HttpStatus.NOT_FOUND));

        List<TrackingEntity> byOrderId = trackingRepository.findByOrder_OrderId(existingOrder.getOrderId());

        return iTrackMapper.toDtoList(byOrderId);
    }
}
