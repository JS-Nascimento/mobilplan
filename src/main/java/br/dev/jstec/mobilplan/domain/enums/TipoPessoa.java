package br.dev.jstec.mobilplan.domain.enums;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_TIPO_PESSOA_INEXISTENTE;
import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_TIPO_PESSOA_NULO;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum TipoPessoa {

    FISICA("FISICA"),
    JURIDICA("JURIDICA");

    private final String descricao;

    public static TipoPessoa of(String descricao) {

        if (isNull(descricao)) {

            throw new DomainException(ERRO_TIPO_PESSOA_NULO);
        }

        return stream(TipoPessoa.values())
                .filter(tipo -> descricao.equals(tipo.descricao))
                .findFirst()
                .orElseThrow(() -> new DomainException(ERRO_TIPO_PESSOA_INEXISTENTE, descricao));
    }
}
