package com.motavieirafelipe.order_management_microservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class OrderException extends Exception {

    private final HttpStatusCode statusCode;

    public OrderException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "{ \"message\" : \"" + super.getMessage() + "\", \"status\" :" + statusCode + "}";
    }
}