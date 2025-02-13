package com.fiap.product_catalog_microservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NullException extends ResponseStatusException {
        public NullException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
}
