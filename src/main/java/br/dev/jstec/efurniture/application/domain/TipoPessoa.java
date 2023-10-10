package br.dev.jstec.efurniture.application.domain;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_PESSOA_INEXISTENTE;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_PESSOA_NULO;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.efurniture.exceptions.BusinessException;
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

            throw new BusinessException(ERRO_TIPO_PESSOA_NULO);
        }

        return stream(TipoPessoa.values())
                .filter(tipo -> descricao.equals(tipo.descricao))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ERRO_TIPO_PESSOA_INEXISTENTE, descricao));
    }
}
