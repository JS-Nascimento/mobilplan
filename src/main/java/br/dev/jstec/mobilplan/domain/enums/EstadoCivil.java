package br.dev.jstec.mobilplan.domain.enums;

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
public enum EstadoCivil {
    SOLTEIRO("Solteiro(a)"),
    CASADO("Casado(a)"),
    DIVORCIADO("Divorciado(a)"),
    VIUVO("Viúvo(a)"),
    UNIAO_ESTAVEL("União Estável"),
    EMPRESA("Empresa");

    private final String descricao;

    public static EstadoCivil of(String descricao) {
        if (isNull(descricao)) {

            throw new DomainException(ERRO_CAMPO_INVALIDO, "Estado Civil");
        }

        return stream(EstadoCivil.values())
                .filter(tipo -> descricao.equalsIgnoreCase(tipo.descricao)
                        || descricao.equalsIgnoreCase(tipo.name()))
                .findFirst()
                .orElseThrow(() -> new DomainException(ERRO_ENTIDADE_INEXISTENTE, "Estado Civil"));
    }
}
