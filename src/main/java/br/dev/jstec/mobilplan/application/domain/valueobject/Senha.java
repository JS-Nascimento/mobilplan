package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SENHA_INVALIDA;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;

public record Senha(String value) {

    public Senha {

        validate(value);
    }

    public static void validate(String value) {

        if (isBlank(value) || value.length() < 6 || value.length() > 10
            || !value.matches(
                "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-])(?!.*[!&*()]).{6,10}$")) {
            throw new BusinessException(ERRO_SENHA_INVALIDA);
        }
    }
}
