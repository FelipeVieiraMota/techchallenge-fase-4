package com.motavieirafelipe.order_management_microservice.exceptions;

import org.springframework.http.HttpStatusCode;

public record Message(String message, HttpStatusCode statusCode) { }
