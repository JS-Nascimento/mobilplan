package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao.enums;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ENTIDADE_INEXISTENTE;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;

public enum TipoPrateleira {

    MOVEL,
    FIXA,
    DIVISORIA_HORIZONTAL;

    public static TipoPrateleira of(String descricao) {

        if (isNull(descricao)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "Tipo de montagem do fundo");
        }

        return stream(TipoPrateleira.values())
                .filter(tipo -> descricao.equalsIgnoreCase(tipo.name()))
                .findFirst()
                .orElseThrow(
                        () -> new DomainException(ERRO_ENTIDADE_INEXISTENTE, "Tipo de Prateleira Interna", descricao));
    }

    public static boolean validate(String descricao) {
        try {
            of(descricao);
            return true;
        } catch (DomainException e) {
            return false;
        }
    }
}
