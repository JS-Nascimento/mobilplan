package br.dev.jstec.mobilplan.domain.valueobject;


import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;


public record Email(String value) {

    public Email {

        if (isBlank(value) || !validar(value)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, Email.class.getSimpleName());
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

    public static Email of(String email) {
        return new Email(email);
    }
}
