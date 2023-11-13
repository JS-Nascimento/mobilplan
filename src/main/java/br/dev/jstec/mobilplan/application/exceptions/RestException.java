package br.dev.jstec.mobilplan.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class RestException extends RuntimeException {

    private final ErrorMessage errorMessage;
}
