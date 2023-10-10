package br.dev.jstec.efurniture.application.constraints;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(BAD_REQUEST)
public class ConstraintException extends RuntimeException {

    public ConstraintException(String message) {
        super(message);
    }
}