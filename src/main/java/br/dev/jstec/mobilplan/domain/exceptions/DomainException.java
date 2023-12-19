package br.dev.jstec.mobilplan.domain.exceptions;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(BAD_REQUEST)
public class DomainException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public DomainException(ErroDeDominio errorEnum) {

        this.errorMessage = ErrorMessage.builder()
            .code(errorEnum.getCode())
            .msg(errorEnum.getMsg())
            .build();
    }

    public DomainException(ErroDeDominio errorEnum, String parametro) {

        this.errorMessage = ErrorMessage.builder()
            .code(errorEnum.getCode())
            .msg(format(errorEnum.getMsg(), parametro))
            .build();
    }

    public DomainException(ErroDeDominio errorEnum, String parametro, String parametro2) {

        this.errorMessage = ErrorMessage.builder()
            .code(errorEnum.getCode())
            .msg(format(errorEnum.getMsg(), parametro, parametro2))
            .build();
    }
}
