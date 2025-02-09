package com.motavieirafelipe.order_management_microservice.controllers;

import com.motavieirafelipe.order_management_microservice.exceptions.Message;
import com.motavieirafelipe.order_management_microservice.exceptions.OrderException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Message> handleOrderNotFoundException(OrderException ex) {
        var response = new Message(ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(response, ex.getStatusCode());
    }
}
