package br.dev.jstec.mobilplan.application.domain.valueobject;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.application.util.StringUtil.normalizarNome;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;

public record Nome(String value) {

    public Nome {

        if (isBlank(value)) {

            throw new BusinessException(ERRO_CAMPO_INVALIDO, Nome.class.getSimpleName());
        }
        value = normalizarNome(value);
    }
}
