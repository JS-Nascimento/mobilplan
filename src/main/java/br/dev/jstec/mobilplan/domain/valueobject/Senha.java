package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_SENHA_INVALIDA;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;

public record Senha(String value) {

    public Senha {

        validate(value);
    }

    public static void validate(String value) {

        if (isBlank(value) || value.length() < 6 || value.length() > 10
            || !value.matches(
                "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-])(?!.*[!&*()]).{6,10}$")) {
            throw new DomainException(ERRO_SENHA_INVALIDA);
        }
    }
}
