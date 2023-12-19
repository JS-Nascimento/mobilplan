package br.dev.jstec.mobilplan.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class RestException extends RuntimeException {

    private final ErrorMessage errorMessage;
}
