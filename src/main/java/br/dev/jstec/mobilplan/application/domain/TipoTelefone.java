package br.dev.jstec.mobilplan.application.domain;

import static br.dev.jstec.mobilplan.application.exceptions.ErroDeNegocio.ERRO_TIPO_TELEFONE_NULO;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.application.exceptions.BusinessException;
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

            throw new BusinessException(ERRO_TIPO_TELEFONE_NULO);
        }

        return stream(values())
            .filter(tipo -> descricao.equals(tipo.descricao))
            .findFirst()
            .orElseGet(() -> null);
    }

    public static TipoTelefone byOrdinal(int ordinal) {

        return stream(values())
            .filter(tipo -> tipo.ordinal() == ordinal)
            .findFirst()
            .orElseGet(() -> null);
    }
}
