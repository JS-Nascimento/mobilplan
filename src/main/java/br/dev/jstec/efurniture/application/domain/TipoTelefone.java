package br.dev.jstec.efurniture.application.domain;

import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_PESSOA_NULO;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_TELEFONE_INEXISTENTE;
import static br.dev.jstec.efurniture.exceptions.ErroDeNegocio.ERRO_TIPO_TELEFONE_NULO;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import br.dev.jstec.efurniture.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTelefone {

    FIXO("FIXO"),
    CELULAR("CELULAR"),
    WHATSAPP("WHATSAPP");

    private final String descricao;

    public static TipoTelefone of(String descricao) {

        if (isNull(descricao)) {

            throw new BusinessException(ERRO_TIPO_PESSOA_NULO);
        }

        return stream(values())
                .filter(tipo -> descricao.equals(tipo.descricao))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ERRO_TIPO_TELEFONE_INEXISTENTE, descricao));
    }

    public static TipoTelefone byOrdinal(int ordinal) {

        if (isNull(ordinal)) {

            throw new BusinessException(ERRO_TIPO_TELEFONE_NULO);
        }

        return stream(values())
                .filter(tipo -> tipo.ordinal() == ordinal)
                .findFirst()
                .orElseThrow(() -> new BusinessException(ERRO_TIPO_TELEFONE_INEXISTENTE, String.valueOf(ordinal)));
    }
}
