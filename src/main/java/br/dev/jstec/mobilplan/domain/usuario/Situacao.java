package br.dev.jstec.mobilplan.domain.usuario;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_SITUACAO_INEXISTENTE;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_SITUACAO_NULA;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum Situacao {

    ATIVO("ATIVO"),
    INATIVO("INATIVO"),
    BLOQUEADO("BLOQUEADO"),
    CANCELADO("CANCELADO");

    private final String descricao;

    public static Situacao of(String descricao) {

        if (isNull(descricao)) {

            throw new DomainException(ERRO_SITUACAO_NULA);
        }

        return stream(Situacao.values())
            .filter(tipo -> descricao.equals(tipo.descricao))
            .findFirst()
            .orElseThrow(() -> new DomainException(ERRO_SITUACAO_INEXISTENTE, descricao));
    }
}
