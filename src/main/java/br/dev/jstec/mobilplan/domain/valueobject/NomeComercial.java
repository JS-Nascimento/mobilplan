package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static java.util.Locale.ROOT;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;

public record NomeComercial(String value) {

    public NomeComercial {

        if (isBlank(value)) {
            throw new DomainException(ERRO_CAMPO_INVALIDO, NomeComercial.class.getSimpleName());
        }
        value = value.toUpperCase(ROOT);
    }
}
