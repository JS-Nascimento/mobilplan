package br.dev.jstec.efurniture.application.domain.marceneiro;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_ID_INVALIDO;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;
import java.util.UUID;

public record MarceneiroId(String value) {

    public MarceneiroId {

        if (isBlank(value)) {

            throw new BusinessException(ERRO_ID_INVALIDO, "marceneiro");
        }
    }

    public static MarceneiroId unique() {

        return new MarceneiroId(UUID.randomUUID().toString());
    }

    public static MarceneiroId with(final String value) {

        try {

            return new MarceneiroId(UUID.fromString(value).toString());

        } catch (NullPointerException | IllegalArgumentException ex) {

            throw new BusinessException(ERRO_ID_INVALIDO, "marceneiro");
        }
    }
}
