package br.dev.jstec.mobilplan.domain.valueobject;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.util.StringUtil.normalizarNome;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;

public record Nome(String value) {

    public Nome {

        if (isBlank(value)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, Nome.class.getSimpleName());
        }
        value = normalizarNome(value);
    }
}
