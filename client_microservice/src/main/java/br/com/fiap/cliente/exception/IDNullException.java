package br.com.fiap.cliente.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IDNullException extends ResponseStatusException {
    public IDNullException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
