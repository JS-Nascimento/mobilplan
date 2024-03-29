package br.dev.jstec.mobilplan.domain.enums;

import static br.dev.jstec.mobilplan.domain.exceptions.ErroDeDominio.ERRO_TIPO_TELEFONE_NULO;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import br.dev.jstec.mobilplan.domain.exceptions.DomainException;
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

            throw new DomainException(ERRO_TIPO_TELEFONE_NULO);
        }

        return stream(values())
                .filter(tipo -> descricao.equalsIgnoreCase(tipo.descricao))
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
