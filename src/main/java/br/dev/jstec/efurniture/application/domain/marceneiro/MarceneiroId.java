package br.dev.jstec.efurniture.application.domain.marceneiro;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static java.util.Objects.isNull;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import java.util.UUID;

public record MarceneiroId(String value) {

    public MarceneiroId {
        if (isNull(value)) {
            throw new BusinessException(ERRO_ID_INVALIDO, "marceneiro");
        }
    }

    public static MarceneiroId unique() {
        return new MarceneiroId(UUID.randomUUID().toString());
    }

    public static MarceneiroId with(final String value) {
        try {

            return new MarceneiroId(UUID.fromString(value).toString());

        } catch (IllegalArgumentException ex) {

            throw new BusinessException(ERRO_ID_INVALIDO, "marceneiro");
        }
    }
}
