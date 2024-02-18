package br.dev.jstec.mobilplan.domain.model.materiaprima;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_CAMPO_INVALIDO;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_ENTIDADE_INEXISTENTE;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum TipoPrecificacao {

    M2("M2"),
    ML("ML"),
    UNIDADE("UNIDADE");

    private final String descricao;

    public static TipoPrecificacao of(String descricao) {

        if (isNull(descricao)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "Precificação");
        }

        return stream(TipoPrecificacao.values())
                .filter(tipo -> descricao.equals(tipo.descricao) || descricao.equals(tipo.name()))
                .findFirst()
                .orElseThrow(() -> new DomainException(ERRO_ENTIDADE_INEXISTENTE, "Precificação"));
    }
}
