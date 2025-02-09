package com.motavieirafelipe.order_management_microservice.controllers;

import com.motavieirafelipe.order_management_microservice.dtos.OrderDto;
import com.motavieirafelipe.order_management_microservice.exceptions.OrderException;
import com.motavieirafelipe.order_management_microservice.services.OrderOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderOrchestrator orderOrchestrator;

    @PostMapping("/sent-event")
    public ResponseEntity<String> pushOrderToQueue(@RequestBody OrderDto order) {
        orderOrchestrator.pushOrderToQueue(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order successfully sent.");
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderOrchestrator.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId) throws OrderException {
        return ResponseEntity.ok(orderOrchestrator.getOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto order) throws URISyntaxException {
        final OrderDto createdOrder = orderOrchestrator.createOrder(order);
        final String uri = "/api/orders" + createdOrder.orderId();
        return ResponseEntity.created(new URI(uri)).body(createdOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable UUID orderId, @RequestBody OrderDto order) throws OrderException {
        final OrderDto createdOrder = orderOrchestrator.updateOrder(orderId, order);
        return ResponseEntity.ok().body(createdOrder);
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderOrchestrator.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
