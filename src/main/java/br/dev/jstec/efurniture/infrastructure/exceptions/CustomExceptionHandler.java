package br.dev.jstec.efurniture.infrastructure.exceptions;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_INFORMACAO_INVALIDA;
import static br.dev.jstec.efurniture.infrastructure.rest.util.JsonUtil.jsonToErrorMessage;
import static java.text.MessageFormat.format;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

import br.dev.jstec.efurniture.application.constraints.ConstraintException;
import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import br.dev.jstec.efurniture.application.exceptions.ErrorMessage;
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

        log.error(ex.getMessage());
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(getErrorMessage(ex));
    }

    private ErrorMessage getErrorMessage(RequestException ex) {

        return jsonToErrorMessage(ex.getResponseBody())
            .filter(error -> nonNull(error.getCode()))
            .map(errorMessage ->
                ErrorMessage.builder()
                    .code(errorMessage.getCode())
                    .msg(errorMessage.getMsg())
                    .serviceName(ex.getServiceName())
                    .response(errorMessage.getResponse())
                    .build())
            .orElseGet(() ->
                ErrorMessage.builder()
                    .code(ErroTecnico.ERRO_PADRAO_INCORRETO.getCode())
                    .msg(ErroTecnico.ERRO_PADRAO_INCORRETO.getMsg())
                    .serviceName(ex.getServiceName())
                    .response(ex.getResponseBody())
                    .build());
    }
}
