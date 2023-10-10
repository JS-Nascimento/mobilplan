package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.efurniture.exceptions.BusinessException;

public record Email(String value) {

    public Email {
        if (isBlank(value) || !validar(value)) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, Email.class.getSimpleName());
        }

        value = value.toLowerCase();
    }

    private static boolean validar(String email) {

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

}
