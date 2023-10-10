package br.dev.jstec.efurniture.application.domain.valueobject;

import static br.dev.jstec.efurniture.application.util.StringUtil.normalizarNome;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_CAMPO_INVALIDO;
import static org.apache.commons.lang3.StringUtils.isBlank;

import br.dev.jstec.efurniture.exceptions.BusinessException;

public record Nome(String value) {

    public Nome {

        if (isBlank(value)) {
            throw new BusinessException(ERRO_CAMPO_INVALIDO, Nome.class.getSimpleName());
        }
        value = normalizarNome(value);
    }
}
