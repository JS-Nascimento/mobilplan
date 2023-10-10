package br.dev.jstec.efurniture.application.domain.marceneiro.user;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static java.util.Objects.isNull;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import java.util.UUID;

public record UserId(String value) {

    public UserId {
        if (isNull(value)) {
            throw new BusinessException(ERRO_ID_INVALIDO, "usuário");
        }
    }

    public static UserId unique() {
        return new UserId(UUID.randomUUID().toString());
    }

    public static UserId with(final String value) {
        try {

            return new UserId(UUID.fromString(value).toString());

        } catch (IllegalArgumentException ex) {

            throw new BusinessException(ERRO_ID_INVALIDO, "usuário");
        }
    }
}
