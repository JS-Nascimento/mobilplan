package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ENTIDADE_INEXISTENTE;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.Getter;

@Getter
public enum TipoFundo {
    ENCAIXADO,
    SOBREPOSTO;

    public static TipoFundo of(String descricao) {

        if (isNull(descricao)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "Tipo de montagem do fundo");
        }

        return stream(TipoFundo.values())
                .filter(tipo -> descricao.equalsIgnoreCase(tipo.name()))
                .findFirst()
                .orElseThrow(
                        () -> new DomainException(ERRO_ENTIDADE_INEXISTENTE, "Tipo de montagem do fundo", descricao));
    }
}
