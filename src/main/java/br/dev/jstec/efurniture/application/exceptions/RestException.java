package br.dev.jstec.efurniture.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class RestException extends RuntimeException {

    private final ErrorMessage errorMessage;
}
