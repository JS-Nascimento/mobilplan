package br.dev.jstec.mobilplan.application.exceptions;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(BAD_REQUEST)
public class BusinessException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public BusinessException(ErroDeNegocio errorEnum) {

        this.errorMessage = ErrorMessage.builder()
            .code(errorEnum.getCode())
            .msg(errorEnum.getMsg())
            .build();
    }

    public BusinessException(ErroDeNegocio errorEnum, String parametro) {

        this.errorMessage = ErrorMessage.builder()
            .code(errorEnum.getCode())
            .msg(format(errorEnum.getMsg(), parametro))
            .build();
    }

    public BusinessException(ErroDeNegocio errorEnum, String parametro, String parametro2) {

        this.errorMessage = ErrorMessage.builder()
            .code(errorEnum.getCode())
            .msg(format(errorEnum.getMsg(), parametro, parametro2))
            .build();
    }
}
