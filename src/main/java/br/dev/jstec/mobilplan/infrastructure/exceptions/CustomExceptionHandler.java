package br.dev.jstec.mobilplan.infrastructure.exceptions;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_INFORMACAO_INVALIDA;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

import br.dev.jstec.mobilplan.application.constraints.ConstraintException;
import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
import br.dev.jstec.mobilplan.application.exceptions.ErrorMessage;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    @ExceptionHandler({
        ConstraintViolationException.class,
        ConstraintException.class})
    ResponseEntity<ErrorMessage> handleConstraintViolationException(Exception ex) {

        var msg = format(ERRO_INFORMACAO_INVALIDA.getMsg(), ex.getMessage());
        log.error(msg);
        return status(BAD_REQUEST)
            .body(ErrorMessage.builder()
                .code(ERRO_INFORMACAO_INVALIDA.getCode())
                .msg(msg)
                .build());
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {

        log.error(format(ERRO_INFORMACAO_INVALIDA.getMsg(), ex.getErrorMessage()));
        return status(BAD_REQUEST)
            .body(ex.getErrorMessage());
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorMessage> handleRequestException(RequestException ex) {

        log.error(ex.getResponseBody().getMsg());
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(getErrorMessage(ex));
    }

    private ErrorMessage getErrorMessage(RequestException ex) {

        var erroName = capitalize(ex.getResponseBody().name().replace("_", " "));
        return ErrorMessage.builder()
            .code(ex.getResponseBody().getCode())
            .msg(erroName)
            .serviceName(ex.getServiceName())
            .response(ex.getResponseBody().getMsg())
            .build();
    }
}
