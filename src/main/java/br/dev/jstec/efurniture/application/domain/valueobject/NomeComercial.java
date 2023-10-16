package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static java.util.Locale.ROOT;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.efurniture.application.exceptions.BusinessException;

public record NomeComercial(String value) {

    public NomeComercial {

        if (isBlank(value)) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, NomeComercial.class.getSimpleName());
        }
        value = value.toUpperCase(ROOT);
    }
}
