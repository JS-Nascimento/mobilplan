package br.dev.jstec.mobilplan.domain.model.configuracaofabricacao;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ENTIDADE_INEXISTENTE;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.Getter;

@Getter
public enum PadraoDeFitagem {
    NENHUM,
    UMA_ALTURA,
    UMA_ALTURA_UMA_LARGURA,
    UMA_ALTURA_DUAS_LARGURAS,
    DUAS_ALTURAS,
    DUAS_ALTURAS_UMA_LARGURA,
    QUATRO_LADOS;

    public static PadraoDeFitagem of(String descricao) {

        if (isNull(descricao)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "Padrao de fitagem");
        }

        return stream(PadraoDeFitagem.values())
                .filter(tipo -> descricao.equalsIgnoreCase(tipo.name()))
                .findFirst()
                .orElseThrow(() -> new DomainException(ERRO_ENTIDADE_INEXISTENTE, "Padrao de fitagem", descricao));
    }
}
