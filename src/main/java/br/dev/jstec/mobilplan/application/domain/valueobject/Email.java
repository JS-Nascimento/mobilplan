package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;

public record Email(String value) {

    public Email {

        if (isBlank(value) || !validar(value)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, Email.class.getSimpleName());
        }

        value = value.toLowerCase();
    }

    private static boolean validar(String email) {

        String regex =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+"
                + "@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?"
                + "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return email.matches(regex);
    }
}