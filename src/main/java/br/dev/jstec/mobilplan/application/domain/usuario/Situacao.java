package br.dev.jstec.mobilplan.application.domain.usuario;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SITUACAO_INEXISTENTE;
import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_SITUACAO_NULA;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
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

            throw new BusinessException(ERRO_SITUACAO_NULA);
        }

        return stream(Situacao.values())
            .filter(tipo -> descricao.equals(tipo.descricao))
            .findFirst()
            .orElseThrow(() -> new BusinessException(ERRO_SITUACAO_INEXISTENTE, descricao));
    }
}